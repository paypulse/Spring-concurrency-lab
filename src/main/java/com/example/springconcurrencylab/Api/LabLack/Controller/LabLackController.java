package com.example.springconcurrencylab.Api.LabLack.Controller;


import com.example.springconcurrencylab.Api.Base.Dto.BaseCtlDto;
import com.example.springconcurrencylab.Api.LabLack.Service.LabLackService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "lab.lack", description = "동시성 제어")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/labLack")
public class LabLackController {

    private final LabLackService labLackService;

    //<editor-fold desc="[POST][TEST] 낙관적 락 ">
    @PostMapping("/init/stack/{qty}")
    public ResponseEntity<BaseCtlDto> postStackInit(
            @Parameter(description = "낙관적 락",required = true)
            @PathVariable("qty") int qty
    ) {
        return labLackService.postStackInit(qty);
    }

    //</editor-fold desc="[POST][TEST] 낙관적 락">




}
