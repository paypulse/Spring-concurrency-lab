package com.example.springconcurrencylab.Define;

public enum StatusCodeEnum {
    INIT("init"),
    SUCCESS("success"),
    CHECK_ID("check.id"),
    VERSION_MISSMATCH("version.mismatch"),
    EXCEPTION_ERROR("exception.error"),
    OPTIMISTIC_LOCK("optimistic.lock"),

    NO_CLASS_SCHEDULED("no.class.scheduled"),

    ALREADY_CLASS_IS_ENDED("class.is.ended"),
    CLASS_IS_ENDED("class.is.ended"),
    CLASS_IS_NOT_ENDED("class.is.not.ended"),
    CLASS_IS_NOT_ONGOING("class.is.not.ongoing"),

    FAIL_CONCURRENCY_LOCK_ISSUE("fail.concurrency.lock.issue"),
    FAIL_UPDATE_CLASS_STATUS("fail.update.class.status"),

    ;
    private final String codeEnum;
    StatusCodeEnum(String codeEnum) {
        this.codeEnum = codeEnum;
    }

    public String getCodeEnum(){
        return codeEnum;
    }

}
