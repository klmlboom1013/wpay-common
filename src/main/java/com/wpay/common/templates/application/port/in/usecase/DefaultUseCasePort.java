package com.wpay.common.templates.application.port.in.usecase;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.dto.BaseResponse;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.port.in.UseCasePort;
import com.wpay.common.templates.domain.Activity;

public interface DefaultUseCasePort extends UseCasePort {

    @Override
    default BaseResponse execute(SelfValidating<?> selfValidating){
        final BaseCommand<?> baseCommand = (BaseCommand<?>) selfValidating;
        Activity activity = Activity.builder()
                .jobCodes(baseCommand.getJobCodes())
                .wtid(baseCommand.getWtid())
                .jnoffcId(baseCommand.getJnoffcId())
                .serverName(baseCommand.getIdcDvdCd())
                .build();

        // TODO: ActivityDefault Domain Set 구현

        return this.defaultRun(activity);
    }

    BaseResponse defaultRun(Activity activity);
}
