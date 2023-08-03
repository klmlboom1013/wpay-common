package com.wpay.common.templates.domain;

import com.wpay.common.global.enums.JobCodes;
import lombok.*;

/**
 * <pre>
 * Adapter.in 으로 부터 인입된 Command dto가 복호화 완료후 UseCase로 넘어 가면서 Activity 도메인에 담겨 진다.
 * Activity 도메인은 UseCase에서 비지니스 처리에 따라 다시 가공 되어 port를 통해 Adapter.out으로 넘어 간다.
 * </pre>
 *
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Activity {
    private final JobCodes jobCodes;
    private final String mid;
    private final String serverName;

    @Setter private EntityTrnsId entityTrnsId;

    @Builder
    public Activity(@NonNull JobCodes jobCodes,
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
