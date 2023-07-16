package com.wpay.common.global.factory.port.out;


import com.wpay.common.global.factory.port.PortDvdCode;

public interface BaseOutPort {
    Object getJobCode();

    Object getVersionCode();

    PortDvdCode getPortDvdCode();
}
