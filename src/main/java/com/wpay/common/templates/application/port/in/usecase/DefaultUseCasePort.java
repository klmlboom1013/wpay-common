package com.wpay.common.templates.application.port.in.usecase;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.BaseResponseV2;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.factory.port.in.UseCasePort;
import com.wpay.common.templates.domain.ActivityDefault;

public interface DefaultUseCasePort extends UseCasePort {

    @Override
    default BaseResponseV2 execute(SelfValidating<?> selfValidating){
        final BaseCommand<?> baseCommand = (BaseCommand<?>) selfValidating;
        ActivityDefault activity = ActivityDefault.builder()
                .jobCodes(baseCommand.getJobCodes())
                .wtid(baseCommand.getWtid())
                .mid(baseCommand.getMid())
                .serverName(baseCommand.getServerName())
                .build();

        // TODO: ActivityDefault Domain Set 구현

        return this.defaultRun(activity);
    }

    BaseResponseV2 defaultRun(ActivityDefault activity);
}
