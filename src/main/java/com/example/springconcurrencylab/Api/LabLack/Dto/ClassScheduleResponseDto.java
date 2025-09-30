package com.example.springconcurrencylab.Api.LabLack.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
public class ClassScheduleResponseDto {
    private Long id;
    private String className;
    private String classStatus;
    private String version;
}
