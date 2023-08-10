package com.wpay.common.templates.application.service;

import com.wpay.common.global.annotation.UseCase;
import com.wpay.common.global.dto.BaseResponse;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.PortOutFactory;
import com.wpay.common.templates.application.port.in.usecase.DefaultUseCasePort;
import com.wpay.common.templates.application.port.in.usecase.DefaultUseCaseVersion;
import com.wpay.common.templates.application.port.out.dto.DefaultExternalMapper;
import com.wpay.common.templates.application.port.out.external.DefaultExternalPort;
import com.wpay.common.templates.application.port.out.persistence.DefaultPersistencePort;
import com.wpay.common.templates.domain.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@UseCase
@RequiredArgsConstructor
public class DefaultService implements DefaultUseCasePort {

    private final PortOutFactory portOutFactory;

    @Override
    public JobCodes getJobCode() {
        // TODO: API 업무 코드에 맞는 JobCodes 세팅
        return JobCodes.JOB_CODE_ZZ;
    }

    @Override
    public DefaultUseCaseVersion getVersionCode() {
        return DefaultUseCaseVersion.v1;
    }

    @Override
    public BaseResponse defaultRun(Activity activity) {

        // TODO: business 로직 구현
        final DefaultExternalPort defaultExternalPort = this.getExternalPort();
        final DefaultPersistencePort defaultPersistencePort = this.getPersistencePort();

        final DefaultExternalMapper externalMapper = defaultExternalPort.defaultRun(activity);
        final DefaultExternalMapper persistenceMapper = defaultExternalPort.defaultRun(activity);

        return BaseResponse.builder()
                .httpStatus(HttpStatus.OK)
                .data(BaseResponse.DefaultData.builder()
                        .wtid("")
                        .jnoffcId("")
                        .build())
                .build();
    }

    private DefaultPersistencePort getPersistencePort() {
        return (DefaultPersistencePort) this.portOutFactory.getPersistencePort(
                this.getVersionCode().toString(),
                this.getJobCode().toString());
    }

    private DefaultExternalPort getExternalPort() {
        return (DefaultExternalPort) this.portOutFactory.getExternalPort(
                this.getVersionCode().toString(),
                this.getJobCode().toString());
    }
}
