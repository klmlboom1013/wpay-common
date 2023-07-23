package com.wpay.common.global.exception;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    private Throwable e;

    private String wtid;
    private String mid;

    public CustomException(ErrorCode errorCode){
        this.errorCode=errorCode;
        this.message=errorCode.getMessage();
    }

    public CustomException(ErrorCode errorCode, String message){
        this.errorCode=errorCode;
        this.message=message;
    }

    public CustomException(ErrorCode errorCode, String message, Throwable e){
        this.errorCode=errorCode;
        this.message=message;
        this.e=e;
    }

    public CustomException(ErrorCode errorCode, String message, String wtid, String mid){
        this.errorCode=errorCode;
        this.wtid=wtid;
        this.mid=mid;
        this.message=message;
    }

    public CustomException(ErrorCode errorCode, String message, Throwable e, String wtid, String mid){
        this.errorCode=errorCode;
        this.wtid=wtid;
        this.mid=mid;
        this.message=message;
        this.e=e;
    }
}
