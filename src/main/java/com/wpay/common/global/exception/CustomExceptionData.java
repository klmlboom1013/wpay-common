package com.wpay.common.global.exception;

import lombok.*;

@Getter
@Value
@Builder
@ToString
@AllArgsConstructor
public class CustomExceptionData {
    ErrorCode errorCode;
    String message;
    Throwable e;
    String wtid;
    String jnoffcId;
    Object data;
}
