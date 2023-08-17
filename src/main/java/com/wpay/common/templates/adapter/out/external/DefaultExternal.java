package com.wpay.common.templates.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.common.templates.application.port.in.dto.SampleEventPlayOnCommand;
import com.wpay.common.templates.application.port.in.usecase.SampleEventUseCaseVersion;
import com.wpay.common.templates.application.port.out.dto.DefaultExternalMapper;
import com.wpay.common.templates.application.port.out.external.DefaultExternalPort;
import com.wpay.common.templates.application.port.out.external.DefaultExternalVersion;
import com.wpay.common.templates.domain.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
public class DefaultExternal implements DefaultExternalPort {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public DefaultExternalVersion getVersionCode() {
        // TODO: Set External Version
        return DefaultExternalVersion.v1;
    }

    @Override
    public DefaultExternalMapper defaultRun(Activity activity) {
        // TODO: External 로직 구현

        log.info(">>> DefaultWeb External 에서 Event Listener를 호출 합니다.");

        final SampleEventPlayOnCommand sampleEventPlayOnCommand =
                SampleEventPlayOnCommand.builder()
                        .eventId("test-event-001")
                        .wtid("WP202308170000001")
                        .version(SampleEventUseCaseVersion.v1.toString())
                        .jnoffcId(activity.getJnoffcId())
                        .idcDvdCd(activity.getIdcDvdCd())
                        .build();
        log.info("EVENT START - {}", sampleEventPlayOnCommand.toString());

        this.eventPublisher.publishEvent(sampleEventPlayOnCommand);

        log.info("EVENT END - {}", sampleEventPlayOnCommand.toString());

        return DefaultExternalMapper.builder()
                .eventId(sampleEventPlayOnCommand.getEventId())
                .data(sampleEventPlayOnCommand.getResultEventData())
                .build();
    }
}
