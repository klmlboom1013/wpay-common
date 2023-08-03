package com.wpay.common.templates.application.port.out.external;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.port.out.ExternalPort;
import com.wpay.common.templates.application.port.out.dto.DefaultExternalMapper;
import com.wpay.common.templates.domain.Activity;

public interface DefaultExternalPort extends ExternalPort {
    @Override default JobCodes getJobCode() {
        // TODO: 업무 구분 코드 지정
        return JobCodes.JOB_CODE_ZZ;
    }

    DefaultExternalMapper defaultRun(Activity activity);
}
