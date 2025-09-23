package com.example.springconcurrencylab.Api.LabLack.Controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "lab.lack", description = "동시성 제어")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lab/lack")
public class LabLackController {


}
