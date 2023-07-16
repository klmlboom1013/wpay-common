package com.wpay.common.global.factory.port.in;


import com.wpay.common.global.factory.port.PortDvdCode;

public interface BaseInPort {
    Object getJobCode();

    Object getVersionCode();

    PortDvdCode getPortDvdCode();
}
