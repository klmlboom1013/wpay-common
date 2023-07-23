package com.wpay.common.templates.application.port.in.usecase;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.templates.global.enums.DefaultVersion;
import lombok.*;

/**
 * Controller API RequestBody
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DefaultCommand extends BaseCommand<DefaultCommand> {

    private String data1;
    private String data2;

    @Override
    public void checkVersion(String version) {
        DefaultVersion.getInstance(version);
    }

    @Override
    public JobCodes getJobCodes() {
        return JobCodes.JOB_CODE_ZZ;
    }
}
