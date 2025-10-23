package com.example.springconcurrencylab.Api.LabLack.Service;

import com.example.springconcurrencylab.Api.Base.Dto.BaseCtlDto;
import org.springframework.http.ResponseEntity;

public interface LabLackService {

    //<editor-fold desc="[GET][TEST] 낙관적 락 ">
    ResponseEntity<BaseCtlDto> getIdOptimistic(Long id);
    //</editor-fold desc="[GET][TEST] 낙관적 락 ">

    //<editor-fold desc="[GET][TEST] 비관적 락 ">
    ResponseEntity<BaseCtlDto> getIdPessimistic(Long id);
    //</editor-fold desc="[GET][TEST] 비관적 락 ">

}
