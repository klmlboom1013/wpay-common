package com.wpay.common.samp.enums;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;

/**
 * RestFul API URI @PathVariable {version} values
 */
public enum VerifyAuthNumbVersion {
    v1;

    public boolean equals(String code) {
        return this.toString().equals(code);
    }

    public static VerifyAuthNumbVersion getInstance(String version) {
        for(VerifyAuthNumbVersion o : VerifyAuthNumbVersion.values())
            if(o.equals(version)) return o;
        throw new CustomException(ErrorCode.INVALID_PARAMETER, "URI Path parameter version: 지원 하지 않은 버전 입니다.");
    }
}
