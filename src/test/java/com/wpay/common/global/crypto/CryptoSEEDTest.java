package com.wpay.common.global.crypto;

import com.wpay.common.global.cryption.CryptoSEED;
import org.junit.jupiter.api.Test;

class CryptoSEEDTest {
    @Test
    public void test() throws Exception {
        System.out.println("======================");
        String enc1 = CryptoSEED.getInstance().dbEncrypt("01020161017");
        System.out.println(CryptoSEED.getInstance().dbDecrypt(enc1));
        System.out.println("======================");
        String enc2 = CryptoSEED.getInstance().dbEncrypt("19841013");
        System.out.println(CryptoSEED.getInstance().dbDecrypt(enc2));
        System.out.println("======================");
        String enc3 = CryptoSEED.getInstance().dbEncrypt("1");
        System.out.println(CryptoSEED.getInstance().dbDecrypt(enc3));
        System.out.println("======================");
        String enc4 = CryptoSEED.getInstance().dbEncryptKR("이현승");
        System.out.println(CryptoSEED.getInstance().dbDecryptKR(enc4));
        System.out.println("======================");
        String enc5 = CryptoSEED.getInstance().dbEncrypt("1345");
        System.out.println(CryptoSEED.getInstance().dbDecrypt(enc5));
        System.out.println("======================");
    }

}