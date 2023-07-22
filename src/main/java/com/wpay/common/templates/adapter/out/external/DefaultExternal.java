package com.wpay.common.templates.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.common.templates.application.port.out.DefaultExternalMapper;
import com.wpay.common.templates.application.port.out.external.DefaultExternalPort;
import com.wpay.common.templates.domain.ActivityDefault;
import com.wpay.common.templates.global.enums.DefaultVersion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
public class DefaultExternal implements DefaultExternalPort {

    @Override
    public DefaultVersion getVersionCode() {
        // TODO: Set External Version
        return DefaultVersion.v1;
    }

    @Override
    public DefaultExternalMapper defaultRun(ActivityDefault activity) {
        // TODO: External 로직 구현
        return null;
    }
}
