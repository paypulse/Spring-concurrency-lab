package com.example.springconcurrencylab.Api.LabLack.Service.Imple;

import com.example.springconcurrencylab.Api.Base.Dto.BaseCtlDto;
import com.example.springconcurrencylab.Api.LabLack.Dto.ClassScheduleResponseDto;
import com.example.springconcurrencylab.Api.LabLack.Dto.CouponResponseDto;
import com.example.springconcurrencylab.Api.LabLack.Repository.ClassScheduleRepository;
import com.example.springconcurrencylab.Api.LabLack.Repository.CouponRepository;
import com.example.springconcurrencylab.Api.LabLack.Service.LabLackService;
import com.example.springconcurrencylab.Define.EntityEnum;
import com.example.springconcurrencylab.Define.StatusCodeEnum;
import com.example.springconcurrencylab.Entity.ClassSchedule;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LabLackServiceImple implements LabLackService {

    private static final Logger log = LoggerFactory.getLogger(LabLackServiceImple.class);
    private final ClassScheduleRepository classScheduleRepository;
    private final CouponRepository couponRepository;


    //<editor-fold desc="[GET][TEST] 낙관적 락 ">
    @Override
    public ResponseEntity<BaseCtlDto> getIdOptimistic(Long id) {
        log.info("optimistic.Lock.Service");
        BaseCtlDto rtn = new BaseCtlDto();
        // 몇번째 시도 인가?
        int chkTry = 0;
        // 최대 몇 번까지 재시도 할 것인가?
        int maxTry = 3;
        try {
            do {
                //id 체크
                if (id == null) {
                    rtn.setSuccess(true);
                    rtn.setMessage(StatusCodeEnum.CHECK_ID.getCodeEnum());
                    rtn.setCode(StatusCodeEnum.SUCCESS);
                    break;
                }
                //현재 강의 조회
                ClassScheduleResponseDto classScheduleResponseDto = classScheduleRepository.findById(id);
                if (classScheduleResponseDto == null) {
                    rtn.setSuccess(true);
                    rtn.setMessage(StatusCodeEnum.NO_CLASS_SCHEDULED.getCodeEnum());
                    rtn.setCode(StatusCodeEnum.NO_CLASS_SCHEDULED);
                    break;
                }
                //현재 강의가 종료 인지 아닌지
                String classStatus = classScheduleResponseDto.getClassStatus();
                Long classVersion =  classScheduleResponseDto.getVersion();
                log.info("check.classStatus.{}", classStatus);
                log.info("check.classVersion {}", classVersion);
                if (classStatus.equals("ENDED")){
                    rtn.setSuccess(true);
                    rtn.setMessage(StatusCodeEnum.ALREADY_CLASS_IS_ENDED.getCodeEnum());
                    rtn.setCode(StatusCodeEnum.ALREADY_CLASS_IS_ENDED);
                    break;
                }
                //현재 강의 상태가 ongoing 상태가 아닌경우
                if (!classStatus.equals("ONGOING")) {
                    rtn.setSuccess(true);
                    rtn.setMessage(StatusCodeEnum.CLASS_IS_NOT_ONGOING.getCodeEnum());
                    rtn.setCode(StatusCodeEnum.CLASS_IS_NOT_ONGOING);
                    break;
                }
                //수업 상태 변경 : 수업 진행 중인 경우 : ongoing  ->  ended로 변경
                boolean updateStatus = classScheduleRepository.setClassEndStatus(id,classVersion);
                log.info("update.class.status.{}", updateStatus);
                if (updateStatus) {
                    // 강의 종료
                    rtn.setSuccess(true);
                    rtn.setMessage(StatusCodeEnum.CLASS_IS_ENDED.getCodeEnum());
                    rtn.setCode(StatusCodeEnum.CLASS_IS_ENDED);
                    break;
                } else {
                    //낙관적 락 발생
                    chkTry++;
                    log.warn(" 낙관적 락 충돌 발생 ({}/{}/{})" , chkTry, maxTry,classVersion);
                    if (chkTry >= maxTry) {
                        rtn.setSuccess(false);
                        rtn.setMessage(StatusCodeEnum.FAIL_CONCURRENCY_LOCK_ISSUE.getCodeEnum());
                        rtn.setCode(StatusCodeEnum.FAIL_CONCURRENCY_LOCK_ISSUE);
                        break;
                    }
                    // 대기 후 재시도
                    Thread.sleep(1000); // 현재 단일 쓰레드를 잠시 멈추는 역할
                    log.info("optimisitic.lock.chkTry {}", chkTry);
                }
                //기본 공통으로 .
                rtn.setSuccess(true);
                rtn.setMessage(StatusCodeEnum.SUCCESS.getCodeEnum());
                rtn.setCode(StatusCodeEnum.SUCCESS);
                break;
            } while (chkTry < maxTry);
        } catch (Exception e) {
            log.error("optimistic.Lock.example.error", e);
            rtn.setSuccess(false);
            rtn.setMessage(StatusCodeEnum.EXCEPTION_ERROR.getCodeEnum());
            rtn.setCode(StatusCodeEnum.EXCEPTION_ERROR);
        }
        return ResponseEntity.ok(rtn);
    }
    //</editor-fold desc="[GET][TEST] 낙관적 락 ">

    //<editor-fold desc="[GET][TEST] 비관적 락 ">
    @Transactional
    @Override
    public ResponseEntity<BaseCtlDto> getIdPessimistic(Long id) {
        BaseCtlDto rtn = new BaseCtlDto();
        String threadName = Thread.currentThread().getName();
        long startTime = System.currentTimeMillis();

        try{
            if (id == null) {
                rtn.setSuccess(true);
                rtn.setMessage(StatusCodeEnum.CHECK_ID.getCodeEnum());
                rtn.setCode(StatusCodeEnum.CHECK_ID);
                return ResponseEntity.ok(rtn);
            }

            log.info("요청.Thread.name.{}./.startTime = {} ", threadName, startTime);

            log.info("id.{}.조회 선점.Thread.name.{}.elapsed={}", id, threadName, System.currentTimeMillis() - startTime);
            //현재 강의 조회
            ClassSchedule classScheduleResponseDto = classScheduleRepository.findByIdPessimistic(id);

            //트랜잭션을 15초 동안 끝내지 않는다.
            //15초 후 메서드가 끝나면, spring이 자동으로 commit.
            //DB가 락을 해제 한다.
            //15초 후에는 다른 쓰레드 들이 row에 접근 가능 하다.
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (classScheduleResponseDto == null) {
                rtn.setSuccess(true);
                rtn.setMessage(StatusCodeEnum.NO_CLASS_SCHEDULED.getCodeEnum());
                rtn.setCode(StatusCodeEnum.NO_CLASS_SCHEDULED);
                return ResponseEntity.ok(rtn);
            }

            // 현재 강의 상태가 ongoing 이 아닌 경우
            String classStatus = String.valueOf(classScheduleResponseDto.getClassStatus());
            if (!classStatus.equals("ONGOING")) {
                rtn.setSuccess(true);
                rtn.setMessage(StatusCodeEnum.CLASS_IS_NOT_ONGOING.getCodeEnum());
                rtn.setCode(StatusCodeEnum.CLASS_IS_NOT_ONGOING);
                return ResponseEntity.ok(rtn);
            }

            //상태 변경
            boolean updateStatue = classScheduleRepository.setPessimisticClassStatus(id);
            if (!updateStatue) {
                rtn.setSuccess(false);
                rtn.setMessage(StatusCodeEnum.FAIL_UPDATE_CLASS_STATUS.getCodeEnum());
                rtn.setCode(StatusCodeEnum.FAIL_UPDATE_CLASS_STATUS);
                return ResponseEntity.ok(rtn);
            }
            log.info("update.classStatus.{}", updateStatue);



            rtn.setSuccess(true);
            rtn.setMessage(StatusCodeEnum.SUCCESS.getCodeEnum());
            rtn.setCode(StatusCodeEnum.SUCCESS);
        }catch (Exception e){
            log.error("pessimistic.lock.example.error", e);
            rtn.setSuccess(false);
            rtn.setMessage(StatusCodeEnum.EXCEPTION_ERROR.getCodeEnum());
            rtn.setCode(StatusCodeEnum.EXCEPTION_ERROR);
        }
        return ResponseEntity.ok(rtn);
    }
    //</editor-fold desc="[GET][TEST] 비관적 락 ">

    //<editor-fold desc="[GET][TEST] 트랜잭션 격리 수준 (READ_UNCOMMITED)">
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public ResponseEntity<BaseCtlDto> getReadUncommited(
            Long id
    ) {
        BaseCtlDto rtn = new BaseCtlDto();
        try
        {
            log.info("get.transaction.lock.read.commited.id.{}", id);
            if (id == null) {
                 rtn.setSuccess(false);
                 rtn.setMessage(StatusCodeEnum.CHECK_ID.getCodeEnum());
                 rtn.setCode(StatusCodeEnum.CHECK_ID);
                 return ResponseEntity.ok(rtn);
            }

            // 첫 조회
            log.info("get.transaction.lock.read.commited.first.id.{}", id);
            CouponResponseDto couponRes = couponRepository.getCouponById(id);
            if (couponRes == null) {
                rtn.setSuccess(true);
                rtn.setMessage(StatusCodeEnum.NO_COUPON_INFO.getCodeEnum());
                rtn.setCode(StatusCodeEnum.NO_COUPON_INFO);
                return ResponseEntity.ok(rtn);
            }

            // 2. 다른 트랜잭션이 개입 힐 시간
            // 3. 두번째 조회(같은 트랜잭션)
            // 4. 응답은 두번째 상태 반환


            rtn.setSuccess(true);
            rtn.setMessage(StatusCodeEnum.SUCCESS.getCodeEnum());
            rtn.setCode(StatusCodeEnum.SUCCESS);
        } catch (Exception e) {
            log.error("get.transaction.lock.read.commited.error.", e);

        }

        return ResponseEntity.ok(rtn);
    }
    //<editor-fold desc="[GET][TEST] 트랜잭션 격리 수준 (READ_UNCOMMITED)">
}
