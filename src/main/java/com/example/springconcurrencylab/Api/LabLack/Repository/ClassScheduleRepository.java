package com.example.springconcurrencylab.Api.LabLack.Repository;

import com.example.springconcurrencylab.Api.LabLack.Dto.ClassScheduleRequestDto;
import com.example.springconcurrencylab.Api.LabLack.Dto.ClassScheduleResponseDto;
import com.example.springconcurrencylab.Define.EntityEnum;
import com.example.springconcurrencylab.Entity.QClassSchedule;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ClassScheduleRepository {
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
    public boolean getClassEndStatus(Long id, Long currentVersion) {
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
    public long saveClassSchedule(ClassScheduleRequestDto classScheduleRequestDto) {
        return queryFactory.insert(qClassSchedule)
                .columns(qClassSchedule.className,
                        qClassSchedule.classStatus,
                        qClassSchedule.version)
                .values(classScheduleRequestDto.getClassName(),
                        classScheduleRequestDto.getClassStatus(),
                        classScheduleRequestDto.getVersion())
                .execute();
    }
    //</editor-fold desc="낙관적 락 테스트 데이터 등록">
}
