package com.wpay.common.templates.global.enums;

import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;

import java.util.Arrays;

public enum DefaultVersion {
    v1;

    public boolean equals(String code) {
        return this.toString().equals(code);
    }

    public static DefaultVersion getInstance(String version) {
        final DefaultVersion[] defaultVersions = new DefaultVersion[1];
        if(Arrays.stream(DefaultVersion.values()).anyMatch(o -> {
            if(Boolean.FALSE.equals(o.toString().equals(version))) return false;
            defaultVersions[0] = o;
            return true;
        })) { return defaultVersions[0]; }
        throw new CustomException(ErrorCode.HTTP_STATUS_400, "URI Path parameter version: 지원 하지 않은 버전 입니다.");
    }
}
