package com.wpay.common.global.crypto;

import com.extrus.jce.provider.ExecureProvider;
import lombok.extern.log4j.Log4j2;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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

    private final SecretKey secretKey;
    private final IvParameterSpec iv;
    private final Cipher cipher;

    protected BaseCryptoSEED(SecretKey secretKey,  IvParameterSpec iv, Cipher cipher) {
        this.secretKey=secretKey;
        this.iv=iv;
        this.cipher=cipher;
    }

    public String encrypt(String plainText) throws Exception {
        return this.encrypt(plainText, StandardCharsets.UTF_8);
    }

    public String decrypt(String cipherText) throws Exception {
        return this.decrypt(cipherText, StandardCharsets.UTF_8);
    }

    public String encryptKR(String plainText) throws Exception {
        return this.encrypt(plainText, Charset.forName("EUC-KR"));
    }

    public String decryptKR(String cipherText) throws Exception {
        return this.decrypt(cipherText, Charset.forName("EUC-KR"));
    }

    public String encrypt(String plainText, Charset charset) throws Exception {
        log.debug("--- SEED Encrypt Start [plainText: {}][charset: {}] ---", plainText, charset.name());
        this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.iv);
        return Base64.getEncoder().encodeToString(this.cipher.doFinal(plainText.getBytes(charset)));
    }

    public String decrypt(String cipherText, Charset charset) throws Exception {
        log.debug("--- SEED Decrypt Start [cipherText: {}][charset: {}] ---", cipherText, charset.name());
        this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.iv);
        return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText.getBytes())), charset);
    }
}
