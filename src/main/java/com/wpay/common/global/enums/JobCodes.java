package com.wpay.common.global.enums;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Log4j2
@Getter
@AllArgsConstructor
public enum JobCodes {
    JOB_CODE_17("17", "MVNO 사업자 확인 요청."),
    JOB_CODE_18("18", "휴대폰 본인 인증 번호 SMS 발송 요청."),
    JOB_CODE_19("19", "휴대폰 본인 인증 인증 번호 확인 요청."),
    JOB_CODE_20("20", "MPI 기준 정보 조회"),
    JOB_CODE_ZZ("ZZ", "테스트 용")
    ;

    @Getter private final String code;
    @Getter private final String description;

    public boolean equals(String code) {
        return this.code.equals(code);
    }


    public static JobCodes getInstance (@NonNull String code) {
        final JobCodes[] result = new JobCodes[1];
        final boolean isMatch = Arrays.stream(JobCodes.values()).anyMatch(o -> {
            if(Boolean.FALSE.equals(o.getCode().equals(code))) return false;
            result[0] = o;
            return true;
        });
        if(Boolean.FALSE.equals(isMatch)) throw new NullPointerException("존재 하지 않은 업무 구분 코드 입니다.");
        return result[0];
    }
}
