package com.wpay.common.global.port.in;


import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.port.PortDvdCode;

public interface UseCasePort extends BaseInPort {

    @Override default PortDvdCode getPortDvdCode() { return PortDvdCode.usecase; }

    Object execute(SelfValidating<?> selfValidating);
}
