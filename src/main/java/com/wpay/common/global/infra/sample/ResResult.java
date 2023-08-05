package com.wpay.common.global.infra.sample;

import org.springframework.http.HttpStatus;

public class ResResult {
    boolean isSuccess;
    HttpStatus status;
    String message;
}
