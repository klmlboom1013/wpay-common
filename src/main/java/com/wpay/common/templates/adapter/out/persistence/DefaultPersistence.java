package com.wpay.common.templates.adapter.out.persistence;

import com.wpay.common.global.annotation.PersistenceAdapter;
import com.wpay.common.templates.application.port.out.DefaultExternalMapper;
import com.wpay.common.templates.application.port.out.DefaultPersistenceMapper;
import com.wpay.common.templates.application.port.out.persistence.DefaultPersistencePort;
import com.wpay.common.templates.domain.ActivityDefault;
import com.wpay.common.templates.global.enums.DefaultVersion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@PersistenceAdapter
@RequiredArgsConstructor
public class DefaultPersistence implements DefaultPersistencePort {

    @Override
    public DefaultVersion getVersionCode() {
        // TODO: Set External Version
        return DefaultVersion.v1;
    }

    @Override
    public DefaultPersistenceMapper defaultsRun(ActivityDefault activity) {
        // TODO: External 로직 구현
        return null;
    }
}
