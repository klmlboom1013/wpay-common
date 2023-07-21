package com.wpay.common.samp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 모빌리언스 연동 업무 구분 코드
 */
@AllArgsConstructor
public enum MobiliansJobCode {

    JOB_CODE_17("17"), // MVNO 사업자 확인 요청.
    JOB_CODE_18("18"), // 휴대폰 본인 인증 번호 SMS 발송 요청.
    JOB_CODE_19("19") // 휴대폰 본인 인증 인증 번호 확인 요청.
    ;

    @Getter
    private final String code;

    public boolean equals(String code) {
        return this.code.equals(code);
    }

    public static MobiliansJobCode getInstance(String code) {
        for(MobiliansJobCode o : MobiliansJobCode.values()){
            if(o.equals(code)){ return o; }
        }
        throw new NullPointerException("존재 하지 않은 업무 구분 코드 입니다.");
    }
}
