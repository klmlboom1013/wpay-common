package com.wpay.common.global.port.out;


import com.wpay.common.global.port.PortDvdCode;

public interface ExternalPort extends BaseOutPort {
    @Override default PortDvdCode getPortDvdCode() { return PortDvdCode.external; }
}
