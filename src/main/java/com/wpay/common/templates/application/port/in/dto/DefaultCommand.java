package com.wpay.common.templates.application.port.in.dto;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.templates.application.port.in.usecase.DefaultUseCaseVersion;
import lombok.*;

/**
 * Controller API RequestBody
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DefaultCommand extends BaseCommand<DefaultCommand> {

    private String data1;
    private String data2;

    public DefaultCommand (String data1, String data2, String wtid, String jnoffcId) {
        super(wtid, jnoffcId, null);
        this.data1=data1;
        this.data2=data2;
    }

    @Override
    public void checkVersion(String version) {
        DefaultUseCaseVersion.getInstance(version);
    }

    @Override
    public JobCodes getJobCodes() {
        return JobCodes.JOB_CODE_ZZ;
    }
}
