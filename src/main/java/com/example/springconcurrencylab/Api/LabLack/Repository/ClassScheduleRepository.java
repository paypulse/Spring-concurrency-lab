package com.example.springconcurrencylab.Api.LabLack.Repository;

import com.example.springconcurrencylab.Api.LabLack.Dto.ClassScheduleRequestDto;
import com.example.springconcurrencylab.Api.LabLack.Dto.ClassScheduleResponseDto;
import com.example.springconcurrencylab.Define.EntityEnum;
import com.example.springconcurrencylab.Entity.ClassSchedule;
import com.example.springconcurrencylab.Entity.QClassSchedule;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ClassScheduleRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    private final QClassSchedule qClassSchedule = QClassSchedule.classSchedule;

    //<editor-fold desc="현재 상태 조회">
    @Transactional(readOnly = true)
    public ClassScheduleResponseDto findById(Long id) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ClassScheduleResponseDto.class,
                                qClassSchedule.id,
                                qClassSchedule.className,
                                qClassSchedule.classStatus.stringValue(),
                                qClassSchedule.version
                        )
                )
                .from(qClassSchedule)
                .where(qClassSchedule.id.eq(id))
                .fetchOne();
    }
    //</editor-fold desc="현재 상태 조회">

    //<editor-fold desc="현재 상태 조회 - DB Lock">
    public ClassScheduleResponseDto findByIdPessimistic(Long id) {
        return queryFactory
                .select(
                        Projections.constructor(
                            ClassScheduleResponseDto.class,
                            qClassSchedule.id,
                            qClassSchedule.className,
                            qClassSchedule.classStatus.stringValue(),
                            qClassSchedule.version
                        )
                )
                .from(qClassSchedule)
                .where(qClassSchedule.id.eq(id))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne();
    }

    //</editor-fold desc="현재 상태 조회 - DB Lock">

    //<editor-fold desc="현재 강의 존재 유무, .fetchFirst() != null 결과가 존재 하면 true, 결과가 없으면 false로 반환">
    @Transactional(readOnly = true)
    public Boolean findExistClass(Long id) {
        Boolean result = queryFactory
                .selectOne()
                .from(qClassSchedule)
                .where(
                        qClassSchedule.id.eq(id),
                            qClassSchedule.classStatus.ne(EntityEnum.ClassStatus.ENDED)
                ).fetchFirst() != null;
        return result;

    }
    //</editor-fold desc="현재 강의 존재 유무, .fetchFirst() != null 결과가 존재 하면 true, 결과가 없으면 false로 반환">

    //<editor-fold desc="낙관적 락 기반 수업 종료">
    @Transactional
    public boolean setClassEndStatus(Long id, Long currentVersion) {
        long updated = queryFactory
                .update(qClassSchedule)
                .set(qClassSchedule.classStatus , EntityEnum.ClassStatus.ENDED )
                .set(qClassSchedule.version , qClassSchedule.version.add(1))
                .where(
                        qClassSchedule.id.eq(id)
                                .and(qClassSchedule.version.eq(currentVersion))
                                .and(qClassSchedule.classStatus.eq(EntityEnum.ClassStatus.ONGOING))
                )
                .execute();
        return updated > 0; // 성공 여부 봔환
    }
    //</editor-fold desc="낙관적 락 기반 수업 종료">

    //<editor-fold desc="낙관적 락 테스트 데이터 등록">
    @Transactional
    public Long saveClassSchedule(ClassScheduleRequestDto classScheduleRequestDto) {
        ClassSchedule entity = ClassSchedule.builder()
                .className(classScheduleRequestDto.getClassName())
                .classStatus(classScheduleRequestDto.getClassStatus())
                .version(classScheduleRequestDto.getVersion())
                .build();
        entityManager.persist(entity);
        return entity.getId();

    }
    //</editor-fold desc="낙관적 락 테스트 데이터 등록">

    //<editor-fold desc="비관적 락 기반 수업 종료">
    @Transactional
    public boolean setPessimisticClassStatus(Long id) {
        long updated = queryFactory
                .update(qClassSchedule)
                .set(qClassSchedule.classStatus , EntityEnum.ClassStatus.ENDED )
                .where(
                        qClassSchedule.id.eq(id)
                )
                .execute();
        return updated > 0; // 성공 여부 봔환
    }
    //</editor-fold desc="낙관적 락 기반 수업 종료">
}
