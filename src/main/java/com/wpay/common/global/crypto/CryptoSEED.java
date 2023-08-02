package com.wpay.common.global.crypto;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Log4j2
public final class CryptoSEED extends BaseCryptoSEED {

    @Getter
    private static final CryptoSEED instance;

    static {
        try {
            instance = new CryptoSEED();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String KEY = "9876543212345678";
    private static final String IV = "wpaycoremodule00";

    private CryptoSEED() throws Exception {
        super(new SecretKeySpec(KEY.getBytes(), "SEED"),
                new IvParameterSpec(IV.getBytes()),
                Cipher.getInstance(ALGORITHM, PROVIDER));
    }
}
