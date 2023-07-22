package com.wpay.common.global.factory.port;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class BasePortFactory {

    /**
     * Port Factory Mapper Key 생성: key = version.jobCode.portDvdCode
     */
    protected final String makeMapperKey (@NonNull String versionCode,
                                          @NonNull String mpiBasicInfoJobCode,
                                          @NonNull String portDvdCode){
        final String result = new StringBuilder()
                .append(versionCode.toUpperCase()).append(".")
                .append(mpiBasicInfoJobCode.toUpperCase()).append(".")
                .append(portDvdCode.toUpperCase()).toString();
        return result.toUpperCase();
    }
}
