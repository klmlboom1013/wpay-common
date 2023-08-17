package com.wpay.common.templates.application.port.in.dto;

import com.wpay.common.global.dto.BaseEventCommand;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.templates.application.port.in.usecase.SampleEventUseCaseVersion;
import lombok.*;


@Getter
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SampleEventPlayOnCommand extends BaseEventCommand<SampleEventPlayOnCommand> {

    String eventId;

    @Builder
    public SampleEventPlayOnCommand(String eventId, String jnoffcId, String wtid, String version, String idcDvdCd) {
        super(jnoffcId, wtid, version, idcDvdCd, null);
        this.eventId = eventId;
    }

    @Override
    public void checkVersion() {
        SampleEventUseCaseVersion.getInstance(super.getVersion());
    }

    @Override
    public JobCodes getJobCodes() {
        return JobCodes.JOB_CODE_EV;
    }
}
