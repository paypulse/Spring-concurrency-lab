package com.example.springconcurrencylab.Api.LabLack.Service.Imple;

import com.example.springconcurrencylab.Api.Base.Dto.BaseCtlDto;
import com.example.springconcurrencylab.Api.LabLack.Service.LabLackService;
import com.example.springconcurrencylab.Define.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LabLackServiceImple implements LabLackService {

    //<editor-fold desc="[POST][TEST] 데이터 재고를 새로 만들어 주는 엔드 포인트">
    @Override
    public ResponseEntity<BaseCtlDto> postStackInit(int qty) {
        BaseCtlDto rtn = new BaseCtlDto();
        try{

            

            rtn.setSuccess(true);
            rtn.setCode(StatusCodeEnum.SUCCESS);
            rtn.setMessage(StatusCodeEnum.SUCCESS.getCodeEnum());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(rtn);
    }
    //</editor-fold desc="[POST][TEST] 데이터 재고를 새로 만들어 주는 엔드 포인트">
}
