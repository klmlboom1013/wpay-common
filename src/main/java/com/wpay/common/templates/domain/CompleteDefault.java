package com.wpay.common.templates.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Builder
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class CompleteDefault {

    private final String eventId;
    private final Object eventResultData;

    @Setter
    private String wtid;

    @Setter
    @JsonProperty("mid")
    private String jnoffcId;
}
