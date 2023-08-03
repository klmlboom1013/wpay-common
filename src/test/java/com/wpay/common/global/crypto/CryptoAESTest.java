package com.wpay.common.global.crypto;

import org.junit.jupiter.api.Test;

class CryptoAESTest {

    @Test
    public void test() throws Exception {
        System.out.println("======================");
        String enc1 = CryptoAES.getInstance().apiEncrypt("01020161017");
        System.out.println(CryptoAES.getInstance().apiDecrypt(enc1));
        System.out.println("======================");
        String enc2 = CryptoAES.getInstance().apiEncrypt("19841013");
        System.out.println(CryptoAES.getInstance().apiDecrypt(enc2));
        System.out.println("======================");
        String enc3 = CryptoAES.getInstance().apiEncrypt("1");
        System.out.println(CryptoAES.getInstance().apiDecrypt(enc3));
        System.out.println("======================");
        String enc4 = CryptoAES.getInstance().apiEncrypt("이현승");
        System.out.println(CryptoAES.getInstance().apiDecrypt(enc4));
        System.out.println("======================");
        String enc5 = CryptoAES.getInstance().apiEncrypt("1345");
        System.out.println(CryptoAES.getInstance().apiDecrypt(enc5));
        System.out.println("======================");
    }
}