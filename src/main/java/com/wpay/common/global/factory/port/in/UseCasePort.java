package com.wpay.common.global.factory.port.in;


import com.wpay.common.global.dto.SelfValidating;
import com.wpay.common.global.factory.port.PortDvdCode;

public interface UseCasePort extends BaseInPort {

    @Override default PortDvdCode getPortDvdCode() { return PortDvdCode.usecase; }

    Object execute(SelfValidating<?> selfValidating);
}
