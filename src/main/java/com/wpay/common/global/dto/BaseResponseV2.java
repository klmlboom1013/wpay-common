package com.wpay.common.global.dto;


import com.wpay.common.global.common.Functions;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@ToString
public class BaseResponseV2 {
    private final String timestamp;
    private final Integer status;
    private final String message;
    private final Object data;

    @Setter
    private String path;

    @Builder
    private BaseResponseV2(@NonNull HttpStatus httpStatus, Object data){
        this.timestamp = Functions.getTimestampMilliSecond.apply(new Date());
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
