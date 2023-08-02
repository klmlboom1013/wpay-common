package com.wpay.common.global.crypto;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Log4j2
public final class CryptoSEEDforDB extends BaseCryptoSEED {

    @Getter
    private static final CryptoSEEDforDB instance;

    static {
        try {
            instance = new CryptoSEEDforDB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String KEY = "OjK6mHE1ajPt5XUuoTvOvw==";
    private static final String IV = "INICISDATABASEIV";

    private CryptoSEEDforDB() throws Exception {
        super(new SecretKeySpec(Base64.getDecoder().decode(KEY.getBytes()), "SEED"),
                new IvParameterSpec(IV.getBytes()),
                Cipher.getInstance(ALGORITHM, PROVIDER));
    }

}
