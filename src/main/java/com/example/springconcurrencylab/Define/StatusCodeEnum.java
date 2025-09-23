package com.example.springconcurrencylab.Define;

public enum StatusCodeEnum {
    INIT("init"),
    SUCCESS("success")


    ;
    private final String codeEnum;
    StatusCodeEnum(String codeEnum) {
        this.codeEnum = codeEnum;
    }

    public String getCodeEnum(){
        return codeEnum;
    }

}
