package com.wpay.common.global.factory.port.out;


import com.wpay.common.global.factory.port.PortDvdCode;

public interface ExternalPort extends BaseOutPort {
    @Override default PortDvdCode getPortDvdCode() { return PortDvdCode.external; }
}
