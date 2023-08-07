package com.wpay.common.global.exception;

import lombok.Getter;

@Getter
public class BadWebClientRequestException extends CustomException {

    private final int responseHttpStatus;
    private final String responseStatusText;
    private final String connUrl;


    public BadWebClientRequestException(BadWebClientRequestExceptionData badWebClientRequestExceptionData) {
        super(badWebClientRequestExceptionData.getCustomExceptionData());
        this.responseHttpStatus = badWebClientRequestExceptionData.getResponseHttpStatus().value();
        this.responseStatusText = badWebClientRequestExceptionData.getResponseStatusText();
        this.connUrl = badWebClientRequestExceptionData.getConnUrl();
    }
}
