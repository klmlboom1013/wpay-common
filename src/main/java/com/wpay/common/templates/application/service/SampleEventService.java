package com.wpay.common.templates.application.service;

import com.wpay.common.global.annotation.UseCase;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.templates.application.port.in.usecase.SampleEventUseCasePort;
import com.wpay.common.templates.application.port.in.usecase.SampleEventUseCaseVersion;
import com.wpay.common.templates.domain.ActivitySampleEvent;
import com.wpay.common.templates.domain.CompleteSampleEvent;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UseCase
public class SampleEventService implements SampleEventUseCasePort {

    @Override
    public JobCodes getJobCode() {
        return JobCodes.JOB_CODE_EV;
    }

    @Override
    public SampleEventUseCaseVersion getVersionCode() {
        return SampleEventUseCaseVersion.v1;
    }

    @Override
    public CompleteSampleEvent searchEvent(ActivitySampleEvent activitySampleEvent) {
        log.info(">>> searchEvent 메소드가 수행 되었습니다.");
        return CompleteSampleEvent.builder()
                .eventId(activitySampleEvent.getEventId())
                .eventStatus("00")
                .build();
    }
}
