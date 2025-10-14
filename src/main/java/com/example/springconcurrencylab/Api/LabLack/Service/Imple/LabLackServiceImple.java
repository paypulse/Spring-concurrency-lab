package com.example.springconcurrencylab.Api.LabLack.Service.Imple;

import com.example.springconcurrencylab.Api.Base.Dto.BaseCtlDto;
import com.example.springconcurrencylab.Api.LabLack.Dto.ClassScheduleResponseDto;
import com.example.springconcurrencylab.Api.LabLack.Repository.ClassScheduleRepository;
import com.example.springconcurrencylab.Api.LabLack.Service.LabLackService;
import com.example.springconcurrencylab.Define.EntityEnum;
import com.example.springconcurrencylab.Define.StatusCodeEnum;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LabLackServiceImple implements LabLackService {

    private static final Logger log = LoggerFactory.getLogger(LabLackServiceImple.class);
    private final ClassScheduleRepository classScheduleRepository;


    //<editor-fold desc="[POST][TEST] 낙관적 락 ">
    @Override
    public ResponseEntity<BaseCtlDto> postIdEndClass(Long id) {
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
                    rtn.setSuccess(false);
                    rtn.setMessage(StatusCodeEnum.CHECK_ID.getCodeEnum());
                    rtn.setCode(StatusCodeEnum.SUCCESS);
                    break;
                }

                //현재 강의 조회
                log.info("check. findExistClass. {}", classScheduleRepository.findExistClass(id));
                log.info("check. findById. {}", classScheduleRepository.findById(id));




                rtn.setSuccess(true);
                rtn.setMessage(StatusCodeEnum.SUCCESS.getCodeEnum());
                rtn.setCode(StatusCodeEnum.SUCCESS);
                break;
            } while (chkTry < maxTry);
        } catch (OptimisticLockException e) {
            chkTry ++;
            // 낙관적 lock 재 시도



        } catch (Exception e) {
            log.error("optimistic.Lock.example.error", e);
            rtn.setSuccess(false);
            rtn.setMessage(StatusCodeEnum.EXCEPTION_ERROR.getCodeEnum());
            rtn.setCode(StatusCodeEnum.EXCEPTION_ERROR);
        }

        return ResponseEntity.ok(rtn);
    }
    //</editor-fold desc="[POST][TEST] 낙관적 락 ">
}
