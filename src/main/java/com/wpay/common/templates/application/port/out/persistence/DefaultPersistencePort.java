package com.wpay.common.templates.application.port.out.persistence;

import com.wpay.common.global.enums.JobCodes;
import com.wpay.common.global.factory.port.out.PersistencePort;
import com.wpay.common.templates.application.port.out.DefaultExternalMapper;
import com.wpay.common.templates.application.port.out.DefaultPersistenceMapper;
import com.wpay.common.templates.domain.ActivityDefault;

public interface DefaultPersistencePort extends PersistencePort {
    @Override default JobCodes getJobCode() {
        // TODO: 업무 구분 코드 지정
        return JobCodes.JOB_CODE_ZZ;
    }

    DefaultPersistenceMapper defaultsRun(ActivityDefault activity);
}
