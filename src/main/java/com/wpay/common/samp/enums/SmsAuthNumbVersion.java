package com.wpay.common.samp.enums;


import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;

/**
 * RestFul API URI @PathVariable {version} values
 */
public enum SmsAuthNumbVersion {
    v1;

    public boolean equals(String code) {
        return this.toString().equals(code);
    }

    public static SmsAuthNumbVersion getInstance(String version) {
        for(SmsAuthNumbVersion o : SmsAuthNumbVersion.values())
            if(o.equals(version)) return o;
        throw new CustomException(ErrorCode.INVALID_PARAMETER, "URI Path parameter version: 지원 하지 않은 버전 입니다.");
    }
}
