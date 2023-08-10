package com.wpay.common.global.exception.customs;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.CustomExceptionData;
import com.wpay.common.global.exception.ErrorCode;

public class JobCodeException extends CustomException {

    private static final String defaultErrorMessage = "유효 하지 않은 업무 구분 값 오류가 발생 했습니다.";

    public JobCodeException() {
        super(CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_500).message(defaultErrorMessage).build());
    }

    public JobCodeException(String wtid, String jnoffcId) {
        super(CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_500).message(defaultErrorMessage).wtid(wtid).jnoffcId(jnoffcId).build());
    }

    public JobCodeException(ErrorCode errorCode, String message, Throwable e, String wtid, String jnoffcId) {
        super(CustomExceptionData.builder().errorCode(ErrorCode.HTTP_STATUS_500).message(defaultErrorMessage).wtid(wtid).jnoffcId(jnoffcId).e(e).build());
    }
}
