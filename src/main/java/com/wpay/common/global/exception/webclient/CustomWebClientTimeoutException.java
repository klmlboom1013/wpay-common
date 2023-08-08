package com.wpay.common.global.exception.webclient;

import com.wpay.common.global.exception.ErrorCode;
import lombok.Getter;

import java.net.URI;

@Getter
public class CustomWebClientTimeoutException extends RuntimeException {
    private final ErrorCode errorCode;
    private final URI uri;
    private final Throwable ex;

    public CustomWebClientTimeoutException (URI uri, Throwable ex) {
        this.errorCode = ErrorCode.HTTP_STATUS_504;
        this.uri=uri;
        this.ex=ex;
    }
}
