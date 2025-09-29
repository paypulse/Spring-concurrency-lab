package com.example.springconcurrencylab.Api.LabLack.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
public class LabLackStockResponseDto {

    private int id;

    private int quantity;

    private int version;

    private int value;


}
