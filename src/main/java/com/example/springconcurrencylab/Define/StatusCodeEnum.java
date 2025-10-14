package com.example.springconcurrencylab.Define;

public enum StatusCodeEnum {
    INIT("init"),
    SUCCESS("success"),
    CHECK_ID("check.id"),
    VERSION_MISSMATCH("version.mismatch"),
    EXCEPTION_ERROR("exception.error"),
    OPTIMISTIC_LOCK("optimistic.lock"),


    ;
    private final String codeEnum;
    StatusCodeEnum(String codeEnum) {
        this.codeEnum = codeEnum;
    }

    public String getCodeEnum(){
        return codeEnum;
    }

}
