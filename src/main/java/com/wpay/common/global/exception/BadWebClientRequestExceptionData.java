package com.wpay.common.global.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Value
@Builder
@ToString
@AllArgsConstructor
public class BadWebClientRequestExceptionData {
    CustomExceptionData customExceptionData;
    HttpStatus responseHttpStatus;
    String responseStatusText;
    String connUrl;
}
