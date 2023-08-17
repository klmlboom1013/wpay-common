package com.wpay.common.templates.domain;

import com.wpay.common.global.enums.JobCodes;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivitySampleEvent {
    private JobCodes jobCodes;
    private String jnoffcId;
    private String idcDvdCd;
    private String eventId;
}
