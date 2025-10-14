package com.example.springconcurrencylab.Api.LabLack.Service;

import com.example.springconcurrencylab.Api.Base.Dto.BaseCtlDto;
import org.springframework.http.ResponseEntity;

public interface LabLackService {

    //<editor-fold desc="[POST][TEST] 낙관적 락 ">
    ResponseEntity<BaseCtlDto> postIdEndClass(Long id);
    //</editor-fold desc="[POST][TEST] 낙관적 락 ">
}
