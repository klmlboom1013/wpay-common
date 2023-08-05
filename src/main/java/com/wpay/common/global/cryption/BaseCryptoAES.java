package com.wpay.common.global.cryption;

import lombok.extern.log4j.Log4j2;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Log4j2
public abstract class BaseCryptoAES {

    static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private Cipher getCipher(int mode, String key, String iv) throws Exception {
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode,
                new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"),
                new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
        return cipher;
    }

    public String encrypt(String plainText, Charset charset, String key, String iv) throws Exception {
        log.debug("--- AES Encrypt Start [plainText: {}][charset: {}] ---", plainText, charset.name());
        return Base64.getEncoder().encodeToString(this.getCipher(Cipher.ENCRYPT_MODE, key, iv).doFinal(plainText.getBytes(charset)));
    }

    public String decrypt(String cipherText, Charset charset, String key, String iv) throws Exception {
        log.debug("--- AES Decrypt Start [cipherText: {}][charset: {}] ---", cipherText, charset.name());
        return new String(this.getCipher(Cipher.DECRYPT_MODE, key, iv).doFinal(Base64.getDecoder().decode(cipherText)), charset);
    }
}