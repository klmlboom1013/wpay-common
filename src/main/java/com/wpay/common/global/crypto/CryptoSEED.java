package com.wpay.common.global.crypto;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Log4j2
public final class CryptoSEED extends BaseCryptoSEED {

    @Getter
    private static final CryptoSEED instance = new CryptoSEED();

    private CryptoSEED() { }

    private static final String DB_ENC_KEY = "OjK6mHE1ajPt5XUuoTvOvw==";
    private static final String DB_ENC_IV = "INICISDATABASEIV";

    public String dbEncrypt(String plainText) throws Exception {
        return super.encrypt(plainText, StandardCharsets.UTF_8, DB_ENC_KEY, DB_ENC_IV);
    }

    public String dbDecrypt(String plainText) throws Exception {
        return super.decrypt(plainText, StandardCharsets.UTF_8, DB_ENC_KEY, DB_ENC_IV);
    }

    public String dbEncrypt(String plainText, Charset charset) throws Exception {
        return super.encrypt(plainText, charset, DB_ENC_KEY, DB_ENC_IV);
    }

    public String dbDecrypt(String plainText, Charset charset) throws Exception {
        return super.decrypt(plainText, charset, DB_ENC_KEY, DB_ENC_IV);
    }

    public String dbEncryptKR(String plainText) throws Exception {
        return super.encrypt(plainText, Charset.forName("EUC-KR"), DB_ENC_KEY, DB_ENC_IV);
    }

    public String dbDecryptKR(String plainText) throws Exception {
        return super.decrypt(plainText, Charset.forName("EUC-KR"), DB_ENC_KEY, DB_ENC_IV);
    }

    public String encryptKR(String plainText, String key, String iv) throws Exception {
        return super.encrypt(plainText, Charset.forName("EUC-KR"), key, iv);
    }

    public String decryptKR(String cipherText, String key, String iv) throws Exception {
        return super.decrypt(cipherText, Charset.forName("EUC-KR"), key, iv);
    }
}
