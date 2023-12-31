package com.wpay.common.global.dto;


import com.wpay.common.global.functions.DateFunctions;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@ToString
public class BaseNoDataResponse {
    private final String timestamp = DateFunctions.getTimestampMilliSecond.apply(new Date());
    private final Integer status;
    private final String message;
    @Setter private String path;

    @Builder
    private BaseNoDataResponse(@NonNull HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }
}
