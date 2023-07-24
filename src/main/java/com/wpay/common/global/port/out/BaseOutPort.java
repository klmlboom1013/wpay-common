package com.wpay.common.global.port.out;


import com.wpay.common.global.port.PortDvdCode;

public interface BaseOutPort {
    Object getJobCode();

    Object getVersionCode();

    PortDvdCode getPortDvdCode();
}
