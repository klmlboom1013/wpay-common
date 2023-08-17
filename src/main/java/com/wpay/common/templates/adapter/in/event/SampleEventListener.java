package com.wpay.common.templates.adapter.in.event;

import com.wpay.common.global.annotation.EventAdapter;
import com.wpay.common.global.port.PortInFactory;
import com.wpay.common.templates.application.port.in.dto.SampleEventPlayOnCommand;
import com.wpay.common.templates.application.port.in.usecase.SampleEventUseCaseVersion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

@Log4j2
@EventAdapter
@RequiredArgsConstructor
public class SampleEventListener {

    private final PortInFactory portInFactory;

    @Order(1)
    @EventListener
    public void sampleEventPlayOn (SampleEventPlayOnCommand sampleEventPlayOnCommand) {
        log.info("event play on 시작... 전에 슬립 3초 대기....");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("3초가 지났다 이제 시작 한다.");

        final Object result = (portInFactory.getUseCasePort(
                SampleEventUseCaseVersion.getInstance(sampleEventPlayOnCommand.getVersion()).toString(),
                sampleEventPlayOnCommand.getJobCodes().toString()))
                .execute(sampleEventPlayOnCommand);

        sampleEventPlayOnCommand.setResultEventData(
                SampleEventPlayOnCommand.ResultEventData.builder()
                        .resultCode("0000").resultMsg("OK").data(result).build());
    }
}
