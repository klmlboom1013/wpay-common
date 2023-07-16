package com.wpay.common.global.factory.port;

import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotBlank;

@Log4j2
public abstract class BasePortFactory {

    /**
     * Port Factory Mapper Key 생성: key = version.jobCode.portDvdCode
     */
    protected final String makeMapperKey (@NotBlank(message = "versionCode: Factory Mapper Key 생성 필수 정보 입니다.") String versionCode,
                                          @NotBlank(message = "mpiBasicInfoJobCode: Factory Mapper Key 생성 필수 정보 입니다.") String mpiBasicInfoJobCode,
                                          @NotBlank(message = "portDvdCode: Factory Mapper Key 생성 필수 정보 입니다.") String portDvdCode){
        final String result = new StringBuilder()
                .append(versionCode.toUpperCase()).append(".")
                .append(mpiBasicInfoJobCode.toUpperCase()).append(".")
                .append(portDvdCode.toUpperCase()).toString();
        return result.toUpperCase();
    }
}
