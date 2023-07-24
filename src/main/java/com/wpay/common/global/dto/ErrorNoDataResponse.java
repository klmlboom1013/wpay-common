package com.wpay.common.global.dto;

import com.wpay.common.global.common.Functions;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Value
@Getter
@ToString
@EqualsAndHashCode
public class ErrorNoDataResponse {
    String timestamp = Functions.getTimestampMilliSecond.apply(new Date());
    Integer status;
    String error;
    String message;

    @Builder
    public ErrorNoDataResponse(HttpStatus httpStatus, String error, String message) {
        this.status = httpStatus.value();
        this.error = error;
        this.message = message;
    }
}
