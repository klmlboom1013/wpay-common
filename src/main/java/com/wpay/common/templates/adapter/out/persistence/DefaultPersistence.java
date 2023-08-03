package com.wpay.common.templates.adapter.out.persistence;

import com.wpay.common.global.annotation.PersistenceAdapter;
import com.wpay.common.templates.application.port.out.dto.DefaultPersistenceMapper;
import com.wpay.common.templates.application.port.out.persistence.DefaultPersistencePort;
import com.wpay.common.templates.application.port.out.persistence.DefaultPersistenceVersion;
import com.wpay.common.templates.domain.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@PersistenceAdapter
@RequiredArgsConstructor
public class DefaultPersistence implements DefaultPersistencePort {

    @Override
    public DefaultPersistenceVersion getVersionCode() {
        return DefaultPersistenceVersion.v1;
    }

    @Override
    public DefaultPersistenceMapper defaultsRun(Activity activity) {
        // TODO: External 로직 구현
        return null;
    }
}
