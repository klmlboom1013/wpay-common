package com.wpay.common.global.factory.port;

import com.wpay.common.global.annotation.Factory;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.factory.port.in.BaseInPort;
import com.wpay.common.global.factory.port.in.UseCasePort;
import com.wpay.common.global.factory.port.out.BaseOutPort;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Factory
public class PortInFactory extends BasePortFactory {

    private final Map<String, Object> portMapper = new HashMap<>();

    public PortInFactory(List<BaseInPort> inPorts) {
        if(CollectionUtils.isEmpty(inPorts)){
            log.error("BaseInPort Interface 가 구현된 Bean 을 찾지 못 했습니다.");
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        inPorts.forEach(in -> {
            final String key = this.makeMapperKey(in.getVersionCode().toString(), in.getJobCode().toString(), in.getPortDvdCode().toString());
            this.portMapper.put(key, in);
        });
    }

    public UseCasePort getUseCasePort (@NonNull String version, @NonNull String jobCodes) {
        final String key = this.makeMapperKey(version, jobCodes, PortDvdCode.usecase.toString());
        return (UseCasePort) Objects.requireNonNull(this.portMapper.get(key), "portMapper 에 저장된 Bean 이 없습니다.");
    }
}
