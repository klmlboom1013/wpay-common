package com.wpay.common.global.factory.port.out;


import com.wpay.common.global.factory.port.PortDvdCode;

public interface PersistencePort extends BaseOutPort {
    @Override default PortDvdCode getPortDvdCode() {
        return PortDvdCode.persistence;
    }
}
