package com.example.springconcurrencylab.Api.LabLack.Dto;

import com.example.springconcurrencylab.Define.EntityEnum;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassScheduleRequestDto {

    private String className;
    private EntityEnum.ClassStatus classStatus;
    private Long version;
}
