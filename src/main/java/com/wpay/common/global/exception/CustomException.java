package com.wpay.common.global.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

@Log4j2
@Setter
@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;
    private String wtid;
    private String mid;

    private Throwable e;
    private Object data;

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

    public CustomException(CustomExceptionData customExceptionData){
        this.errorCode = customExceptionData.getErrorCode();
        this.message = customExceptionData.getMessage();
        this.e = customExceptionData.getE();
        this.wtid = customExceptionData.getWtid();
        this.mid = customExceptionData.getMid();
        this.data=customExceptionData.getData();
    }

    public String getDbRecodeMessage() {
        if (Objects.nonNull(this.e)) { return String.format("%s: %s", this.e.getClass().getSimpleName(), this.e.getMessage()); }
        else if (Strings.isNotBlank(this.message)) { return this.message; }
        else if (Objects.nonNull(this.errorCode)) { this.errorCode.getMessage(); }
        return ErrorCode.HTTP_STATUS_500.getMessage();
    }
}
