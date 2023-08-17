package com.wpay.common.templates.application.port.in.usecase;

import com.wpay.common.global.dto.BaseEventCommand;
import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.exception.CustomException;
import com.wpay.common.global.exception.ErrorCode;
import com.wpay.common.global.port.in.UseCasePort;
import com.wpay.common.templates.domain.ActivitySampleEvent;
import com.wpay.common.templates.domain.CompleteSampleEvent;
import org.springframework.beans.BeanUtils;

public interface SampleEventUseCasePort extends UseCasePort {


    @Override
    default Object execute(SelfValidating<?> selfValidating) {
        if(Boolean.FALSE.equals(selfValidating instanceof BaseEventCommand)) {
            throw new CustomException(ErrorCode.HTTP_STATUS_500, "Command 구현 대상 객체 정책 위반 입니다.");
        }
        final ActivitySampleEvent activitySampleEvent = ActivitySampleEvent.builder().build();
        BeanUtils.copyProperties(selfValidating, activitySampleEvent);

        return this.searchEvent(activitySampleEvent);
    }

    CompleteSampleEvent searchEvent(ActivitySampleEvent activitySampleEvent);
}
