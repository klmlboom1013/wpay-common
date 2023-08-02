package com.wpay.common.global.crypto;

import org.junit.jupiter.api.Test;

class CryptoAESTest {

    @Test
    public void test() throws Exception {
        System.out.println("======================");
        String enc1 = CryptoAES.getInstance().encrypt("01020161017");
        System.out.println(CryptoAES.getInstance().decrypt(enc1));
        System.out.println("======================");
        String enc2 = CryptoAES.getInstance().encrypt("19841013");
        System.out.println(CryptoAES.getInstance().decrypt(enc2));
        System.out.println("======================");
        String enc3 = CryptoAES.getInstance().encrypt("1");
        System.out.println(CryptoAES.getInstance().decrypt(enc3));
        System.out.println("======================");
        String enc4 = CryptoAES.getInstance().encrypt("이현승");
        System.out.println(CryptoAES.getInstance().decrypt(enc4));
        System.out.println("======================");
        String enc5 = CryptoAES.getInstance().encrypt("1345");
        System.out.println(CryptoAES.getInstance().decrypt(enc5));
        System.out.println("======================");
    }
}