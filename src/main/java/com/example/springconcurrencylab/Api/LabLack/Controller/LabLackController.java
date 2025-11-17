package com.example.springconcurrencylab.Api.LabLack.Controller;


import com.example.springconcurrencylab.Api.Base.Dto.BaseCtlDto;
import com.example.springconcurrencylab.Api.LabLack.Service.LabLackService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "lab.lack", description = "동시성 제어")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/labLack")
@Slf4j
public class LabLackController {

    private final LabLackService labLackService;

    //<editor-fold desc="[GET][TEST] 낙관적 락 ">
    @GetMapping("/optimistic")
    public ResponseEntity<BaseCtlDto> getIdOptimistic(
            @Parameter(description = "낙관적 락",required = true)
            @RequestParam("id") Long id
    ) {
        log.info("optimistic.lock.example");
        return labLackService.getIdOptimistic(id);
    }
    //</editor-fold desc="[GET][TEST] 낙관적 락">

    //<editor-fold desc="[GET][TEST] 비관적 락 ">
    @GetMapping("/pessimistic")
    public ResponseEntity<BaseCtlDto> getIdPessimistic(
            @Parameter(description = "낙관적 락",required = true)
            @RequestParam("id") Long id
    ) {
        log.info("pessimistic.lock.example");
        return labLackService.getIdPessimistic(id);
    }
    //</editor-fold desc="[GET][TEST] 비관적 락 ">

    //<editor-fold desc="[GET][TEST] 트랜잭션 격리 수준 실험(1)">
    @GetMapping("/trasnsactionIsolation")
    public ResponseEntity<BaseCtlDto> getTransactionIsolation(){
        log.info("trasnsactionIsolation.example");
        return labLackService.getTransactionIsolation();
    }
    //</editor-fold desc="[GET][TEST] 트랜잭션 격리 수준 실험(1)">





}
