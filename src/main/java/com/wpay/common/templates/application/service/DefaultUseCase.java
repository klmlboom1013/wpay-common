package com.wpay.common.templates.application.service;

import com.wpay.common.global.annotation.UseCase;
import com.wpay.common.global.dto.BaseResponseV2;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.factory.port.PortOutFactory;
import com.wpay.common.templates.application.port.in.usecase.DefaultUseCasePort;
import com.wpay.common.templates.application.port.out.DefaultExternalMapper;
import com.wpay.common.templates.application.port.out.external.DefaultExternalPort;
import com.wpay.common.templates.application.port.out.persistence.DefaultPersistencePort;
import com.wpay.common.templates.domain.ActivityDefault;
import com.wpay.common.templates.global.enums.DefaultVersion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@UseCase
@RequiredArgsConstructor
public class DefaultUseCase implements DefaultUseCasePort {

    private final PortOutFactory portOutFactory;

    @Override
    public JobCodes getJobCode() {
        // TODO: API 업무 코드에 맞는 JobCodes 세팅
        return JobCodes.JOB_CODE_ZZ;
    }

    @Override
    public DefaultVersion getVersionCode() {
        return DefaultVersion.v1;
    }

    @Override
    public BaseResponseV2 defaultRun(ActivityDefault activity) {

        // TODO: business 로직 구현
        final DefaultExternalPort defaultExternalPort = this.getExternalPort();
        final DefaultPersistencePort defaultPersistencePort = this.getPersistencePort();

        final DefaultExternalMapper externalMapper = defaultExternalPort.defaultRun(activity);
        final DefaultExternalMapper persistenceMapper = defaultExternalPort.defaultRun(activity);

        return BaseResponseV2.builder()
                .httpStatus(HttpStatus.OK)
                .data(BaseResponseV2.DefaultData.builder()
                        .wtid("")
                        .mid("")
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
