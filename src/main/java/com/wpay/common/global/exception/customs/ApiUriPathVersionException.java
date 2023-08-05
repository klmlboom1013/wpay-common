package com.wpay.common.global.exception.customs;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;

public class ApiUriPathVersionException extends CustomException {

    private static final String defaultErrorMessage = "API URI Version 정보를 다시 확인해 보시기 바랍니다.";

    public ApiUriPathVersionException() {
        super(ErrorCode.HTTP_STATUS_404, defaultErrorMessage);
    }

    public ApiUriPathVersionException(String wtid, String mid) {
        super(ErrorCode.HTTP_STATUS_404, defaultErrorMessage, wtid, mid);
    }

    public ApiUriPathVersionException(ErrorCode errorCode, String message, Throwable e, String wtid, String mid) {
        super(errorCode, message, e, wtid, mid);
    }
}
