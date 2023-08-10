package com.wpay.common.global.exception.customs;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.CustomExceptionData;
import com.wpay.common.global.exception.ErrorCode;

public class ApiUriPathVersionException extends CustomException {

    private static final String defaultErrorMessage = "API URI Version 정보를 다시 확인해 보시기 바랍니다.";

    public ApiUriPathVersionException() {
        super(CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_404).message(defaultErrorMessage).build());
    }

    public ApiUriPathVersionException(String wtid, String jnoffcId) {
        super(CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_404).message(defaultErrorMessage).wtid(wtid).jnoffcId(jnoffcId).build());
    }

    public ApiUriPathVersionException(ErrorCode errorCode, String message, Throwable e, String wtid, String jnoffcId) {
        super(CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_404).message(defaultErrorMessage).wtid(wtid).jnoffcId(jnoffcId).e(e).build());
    }
}
