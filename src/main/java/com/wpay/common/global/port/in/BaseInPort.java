package com.wpay.common.global.port.in;


import com.wpay.common.global.port.PortDvdCode;

public interface BaseInPort {
    Object getJobCode();

    Object getVersionCode();

    PortDvdCode getPortDvdCode();
}
