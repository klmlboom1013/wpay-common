package com.wpay.common.global.dto;

import com.wpay.common.global.common.DateFunctions;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Value
@Getter
@ToString
@EqualsAndHashCode
public class ErrorResponse {
    String timestamp = DateFunctions.getTimestampMilliSecond.apply(new Date());
    Integer status;
    String error;
    String message;
    Object data;

    @Builder
    public ErrorResponse(HttpStatus httpStatus, String error, String message, Object data) {
        this.status = httpStatus.value();
        this.error = error;
        this.message = message;
        this.data=data;
    }

    @Value
    @Builder
    @AllArgsConstructor
    @Getter
    public static class DefaultData {
        String wtid;
        String mid;
    }
}
