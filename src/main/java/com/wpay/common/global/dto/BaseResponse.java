package com.wpay.common.global.dto;


import com.wpay.common.global.functions.DateFunctions;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;


@Getter
@ToString
public class BaseResponse {
    private final String timestamp = DateFunctions.getTimestampMilliSecond.apply(new Date());
    private final Integer status;
    private final String message;
    private final Object data;

    @Setter private String path;

    @Builder
    private BaseResponse(@NonNull HttpStatus httpStatus, Object data){
        this.status=httpStatus.value();
        this.message=httpStatus.getReasonPhrase();
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
