package com.wpay.common.global.factory.port;

import com.wpay.common.global.annotation.Factory;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.factory.port.out.ExternalPort;
import com.wpay.common.global.factory.port.out.PersistencePort;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Factory
public class PortOutFactory extends BasePortFactory {

    private final Map<String, Object> portMapper = new HashMap<>();

    public PortOutFactory(List<ExternalPort> externalPorts, List<PersistencePort> persistencePorts) {
        if(CollectionUtils.isEmpty(externalPorts))
            throw new CustomException(ErrorCode.HTTP_STATUS_500, "ExternalPort Interface 가 구현된 Bean 을 찾지 못 했습니다.");
        if(CollectionUtils.isEmpty(persistencePorts))
            throw new CustomException(ErrorCode.HTTP_STATUS_500, "PersistencePort Interface 가 구현된 Bean 을 찾지 못 했습니다.");

        externalPorts.forEach(out -> {
            final String key = this.makeMapperKey(out.getVersionCode().toString(), out.getJobCode().toString(),out.getPortDvdCode().toString());
            log.debug(">> Registration PortOutFactory ExternalPort KEY: {}", key);
            this.portMapper.put(key, out);
        });

        persistencePorts.forEach(out -> {
            final String key = this.makeMapperKey(out.getVersionCode().toString(), out.getJobCode().toString(),out.getPortDvdCode().toString());
            log.debug(">> Registration PortOutFactory PersistencePort KEY: {}", key);
            this.portMapper.put(key, out);
        });
    }

    public ExternalPort getExternalPort (@NonNull String version, @NonNull String jobCodes) {
        final String key = this.makeMapperKey(version, jobCodes, PortDvdCode.external.toString());
        log.debug(">> Fetch PortOutFactory ExternalPort KEY: {}", key);
        return (ExternalPort) Objects.requireNonNull(this.portMapper.get(key),
                "PortOutFactory.portMapper 에서 조건에 맞는 ExternalPort를 찾지 못 했습니다.");
    }

    public PersistencePort getPersistencePort (@NonNull String version, @NonNull String jobCodes) {
        final String key = this.makeMapperKey(version, jobCodes, PortDvdCode.persistence.toString());
        log.debug(">> Fetch PortOutFactory PersistencePort KEY: {}", key);
        return (PersistencePort) Objects.requireNonNull(this.portMapper.get(key),
                "PortOutFactory.portMapper 에서 조건에 맞는 PersistencePort를 찾지 못 했습니다.");
    }
}
