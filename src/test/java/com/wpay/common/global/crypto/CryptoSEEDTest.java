package com.wpay.common.global.crypto;

import org.junit.jupiter.api.Test;

class CryptoSEEDforDBTest {
    @Test
    public void test() throws Exception {
        System.out.println("======================");
        String enc1 = CryptoSEEDforDB.getInstance().encrypt("01020161017");
        System.out.println(CryptoSEEDforDB.getInstance().decrypt(enc1));
        System.out.println("======================");
        String enc2 = CryptoSEEDforDB.getInstance().encrypt("19841013");
        System.out.println(CryptoSEEDforDB.getInstance().decrypt(enc2));
        System.out.println("======================");
        String enc3 = CryptoSEEDforDB.getInstance().encrypt("1");
        System.out.println(CryptoSEEDforDB.getInstance().decrypt(enc3));
        System.out.println("======================");
        String enc4 = CryptoSEEDforDB.getInstance().encryptKR("이현승");
        System.out.println(CryptoSEEDforDB.getInstance().decryptKR(enc4));
        System.out.println("======================");
        String enc5 = CryptoSEEDforDB.getInstance().encrypt("1345");
        System.out.println(CryptoSEEDforDB.getInstance().decrypt(enc5));
        System.out.println("======================");
    }

    @Test
    public void test2() throws Exception {
        System.out.println("======================");
        String enc1 = CryptoSEED.getInstance().encrypt("01020161017");
        System.out.println(CryptoSEED.getInstance().decrypt(enc1));
        System.out.println("======================");
        String enc2 = CryptoSEED.getInstance().encrypt("19841013");
        System.out.println(CryptoSEED.getInstance().decrypt(enc2));
        System.out.println("======================");
        String enc3 = CryptoSEED.getInstance().encrypt("1");
        System.out.println(CryptoSEED.getInstance().decrypt(enc3));
        System.out.println("======================");
        String enc4 = CryptoSEED.getInstance().encryptKR("이현승");
        System.out.println(CryptoSEED.getInstance().decryptKR(enc4));
        System.out.println("======================");
        String enc5 = CryptoSEED.getInstance().encrypt("1345");
        System.out.println(CryptoSEED.getInstance().decrypt(enc5));
        System.out.println("======================");
    }
}