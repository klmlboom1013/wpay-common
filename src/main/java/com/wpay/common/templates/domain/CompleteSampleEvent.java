package com.wpay.common.templates.domain;

import lombok.*;

@Getter
@Builder
@Value
@ToString
@EqualsAndHashCode(callSuper = false)
public class CompleteSampleEvent {
    String eventId;
    String eventStatus;
}
