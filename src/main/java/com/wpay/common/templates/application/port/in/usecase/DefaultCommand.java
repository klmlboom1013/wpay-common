package com.wpay.common.templates.application.port.in.usecase;

import com.wpay.common.global.dto.BaseCommand;
import com.wpay.common.global.enums.JobCodes;
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
    public boolean checkVersion(String version) {
        return false;
    }

    @Override
    public JobCodes getJobCodes() {
        // TODO: API 업무 코드 지정.
        return null;
    }
}
