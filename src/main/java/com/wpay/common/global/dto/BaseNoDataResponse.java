package com.wpay.common.global.dto;


import com.wpay.common.global.common.Functions;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@ToString
public class BaseNoDataResponse {
    private final String timestamp;
    private final Integer status;
    private final String message;
    @Setter private String path;

    @Builder
    private BaseNoDataResponse(@NonNull HttpStatus httpStatus) {
        this.timestamp = Functions.getTimestampMilliSecond.apply(new Date());
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }
}
