package com.wpay.common.templates.adapter.out.external;

import com.wpay.common.global.annotation.ExternalAdapter;
import com.wpay.common.templates.application.port.out.dto.DefaultExternalMapper;
import com.wpay.common.templates.application.port.out.external.DefaultExternalPort;
import com.wpay.common.templates.application.port.out.external.DefaultExternalVersion;
import com.wpay.common.templates.domain.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExternalAdapter
@RequiredArgsConstructor
public class DefaultExternal implements DefaultExternalPort {

    @Override
    public DefaultExternalVersion getVersionCode() {
        // TODO: Set External Version
        return DefaultExternalVersion.v1;
    }

    @Override
    public DefaultExternalMapper defaultRun(Activity activity) {
        // TODO: External 로직 구현
        return null;
    }
}
