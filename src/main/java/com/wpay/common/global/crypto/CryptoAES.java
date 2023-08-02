package com.wpay.common.global.crypto;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Log4j2
public final class CryptoAES extends BaseCryptoAES {

    @Getter
    private static final CryptoAES instance;

    static {
        try {
            instance = new CryptoAES();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String KEY = "12345678901234567890123456789012";
    private static final String IV = "wpaycoremodule00";

    private CryptoAES() throws Exception {
        super(new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES"),
                new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)),
                Cipher.getInstance(ALGORITHM));
    }
}