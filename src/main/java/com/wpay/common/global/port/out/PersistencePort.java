package com.wpay.common.global.port.out;


import com.wpay.common.global.port.PortDvdCode;

public interface PersistencePort extends BaseOutPort {
    @Override default PortDvdCode getPortDvdCode() {
        return PortDvdCode.persistence;
    }
}
