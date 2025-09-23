package com.example.springconcurrencylab.Api.Base.Dto;

import com.example.springconcurrencylab.Define.StatusCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseCtlDto {
    @Schema(description = "API 성공 여부 (true: 오류 없음 , false:  오류 발생)")
    private Boolean success = false;

    @Schema(description = "오류 발생 시 오류 코드 ")
    private StatusCodeEnum code = StatusCodeEnum.INIT;

    @Schema(description = "상황에 대한 추가 메세지 (성공/실패와 상관 없으며, 각 컨트롤러에 추가 안내)")
    private String message = "";

}
