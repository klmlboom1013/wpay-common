package com.wpay.common.global.exception.webclient;

import com.wpay.common.global.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Setter
@Getter
public class CustomWebClientTimeoutException extends RuntimeException {
    private final ErrorCode errorCode;
    private final URI uri;
    private final Throwable ex;
    private Object mapper;

    public CustomWebClientTimeoutException (URI uri, Throwable ex) {
        this.errorCode = ErrorCode.HTTP_STATUS_504;
        this.uri=uri;
        this.ex=ex;
    }

    public CustomWebClientTimeoutException (URI uri, Throwable ex, Object mapper) {
        this.errorCode = ErrorCode.HTTP_STATUS_504;
        this.uri=uri;
        this.ex=ex;
        this.mapper = mapper;
    }
}
