package com.example.springconcurrencylab.Api.LabLack.Dto;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountsResponseDto {

    private Integer id;
    private String ownerName;
    private Integer balance;
}
