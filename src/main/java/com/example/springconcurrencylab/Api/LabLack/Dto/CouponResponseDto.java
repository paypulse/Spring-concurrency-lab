package com.example.springconcurrencylab.Api.LabLack.Dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponResponseDto {

    private Integer id;
    private String couponCode;
    private Integer issueCount;
    private Integer limitCount;
    private Long version;
}
