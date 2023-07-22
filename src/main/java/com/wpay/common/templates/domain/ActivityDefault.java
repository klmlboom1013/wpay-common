package com.wpay.common.templates.domain;

import com.wpay.common.global.enums.JobCodes;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ActivityDefault {
    private final JobCodes jobCodes;
    private final String mid;
    private final String serverName;

    @Setter private EntityTrnsId entityTrnsId;

    @Builder
    public ActivityDefault(@NonNull JobCodes jobCodes,
                           @NonNull String wtid,
                           @NonNull String mid,
                           @NonNull String serverName) {
        this.jobCodes = jobCodes;
        this.mid = mid;
        this.serverName = serverName;

        this.entityTrnsId = EntityTrnsId.builder().wtid(wtid).build();
    }

    @Value
    @Getter
    @Builder
    public static class EntityTrnsId {
        String wtid;
        Long srlno;
    }
}
