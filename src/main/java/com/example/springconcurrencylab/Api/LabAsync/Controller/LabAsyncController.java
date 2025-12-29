package com.example.springconcurrencylab.Api.LabAsync.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "lab.async", description = "동시성 제어")
@RequiredArgsConstructor
//TODO. Vaadin 추가 후 RestController 지움, 왜냐, Spring MVC가 먼저 가로 채니까
@RestController
@RequestMapping("/api/labAsync")
@Slf4j
public class LabAsyncController {
}
