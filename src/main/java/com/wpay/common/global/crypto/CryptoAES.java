package com.wpay.common.global.crypto;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Log4j2
public final class CryptoAES extends BaseCryptoAES {

    @Getter
    private static final CryptoAES instance = new CryptoAES();

    private static final String API_ENC_KEY = "12345678901234567890123456789012";
    private static final String API_ENC_IV = "wpaycoremodule00";

    private CryptoAES() { }

    public String encrypt(String plainText) throws Exception {
        return super.encrypt(plainText, StandardCharsets.UTF_8, API_ENC_KEY, API_ENC_IV);
    }

    public String decrypt(String plainText) throws Exception {
        return super.decrypt(plainText, StandardCharsets.UTF_8, API_ENC_KEY, API_ENC_IV);
    }

    public String encrypt(String plainText, Charset charset) throws Exception {
        return super.encrypt(plainText, charset, API_ENC_KEY, API_ENC_IV);
    }

    public String decrypt(String plainText, Charset charset) throws Exception {
        return super.decrypt(plainText, charset, API_ENC_KEY, API_ENC_IV);
    }

    public String encryptKR(String plainText) throws Exception {
        return super.encrypt(plainText, Charset.forName("EUC-KR"), API_ENC_KEY, API_ENC_IV);
    }

    public String decryptKR(String plainText) throws Exception {
        return super.decrypt(plainText, Charset.forName("EUC-KR"), API_ENC_KEY, API_ENC_IV);
    }
}