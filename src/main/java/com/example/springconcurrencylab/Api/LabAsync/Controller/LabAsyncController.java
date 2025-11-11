package com.example.springconcurrencylab.Api.LabAsync.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "lab.async", description = "동시성 제어")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/labAsync")
@Slf4j
public class LabAsyncController {
}
