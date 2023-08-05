package com.wpay.common.global.cryption;

import com.extrus.jce.provider.ExecureProvider;
import lombok.extern.log4j.Log4j2;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Security;
import java.util.Base64;
import java.util.Objects;

@Log4j2
public abstract class BaseCryptoSEED {

    static final String ALGORITHM = "SEED/CBC/PKCS5Padding";
    static final String PROVIDER = "ExecureCrypto";

    static {
        if(Objects.isNull(Security.getProvider(PROVIDER))){
            log.debug("ExecureProvider initialize compile.");
            Security.addProvider(new ExecureProvider());
        }
    }

    private Cipher getCipher(int mode, String key, String iv) throws Exception {
        final Cipher cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
        cipher.init(mode,
                new SecretKeySpec(Base64.getDecoder().decode(key.getBytes()), "SEED"),
                new IvParameterSpec(iv.getBytes()));
        return cipher;
    }

    public String encrypt(String plainText, Charset charset, String key, String iv) throws Exception {
        log.debug("--- SEED Encrypt Start [plainText: {}][charset: {}] ---", plainText, charset.name());
        return Base64.getEncoder().encodeToString(this.getCipher(Cipher.ENCRYPT_MODE, key, iv).doFinal(plainText.getBytes(charset)));
    }

    public String decrypt(String cipherText, Charset charset, String key, String iv) throws Exception {
        log.debug("--- SEED Decrypt Start [cipherText: {}][charset: {}] ---", cipherText, charset.name());
        return new String(this.getCipher(Cipher.DECRYPT_MODE, key, iv).doFinal(Base64.getDecoder().decode(cipherText.getBytes())), charset);
    }
}
