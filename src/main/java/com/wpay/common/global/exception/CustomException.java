package com.wpay.common.global.exception;

import lombok.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Setter
@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;
    private String wtid;
    private String mid;

    private Throwable e;

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
    }
}
