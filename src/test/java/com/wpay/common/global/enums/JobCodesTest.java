package com.wpay.common.global.enums;

import org.junit.jupiter.api.Test;

class JobCodesTest {

    @Test
    void testEquals() {
    }

    @Test
    void getInstance() {
        System.out.println(">>> Result : " + JobCodes.getInstance("18"));
    }
}