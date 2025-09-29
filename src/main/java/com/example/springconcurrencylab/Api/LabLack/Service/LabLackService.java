package com.example.springconcurrencylab.Api.LabLack.Service;

import com.example.springconcurrencylab.Api.Base.Dto.BaseCtlDto;
import org.springframework.http.ResponseEntity;

public interface LabLackService {

    //<editor-fold desc="[POST][TEST] 데이터 재고를 새로 만들어 주는 엔드 포인트">
    ResponseEntity<BaseCtlDto> postStackInit(int qty);
    //</editor-fold desc="[POST][TEST] 데이터 재고를 새로 만들어 주는 엔드 포인트">
}
