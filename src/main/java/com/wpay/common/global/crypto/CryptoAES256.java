package com.wpay.common.global.crypto;

import lombok.Getter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


public final class CryptoAES256 {

    @Getter private static final CryptoAES256 instance = new CryptoAES256();

    private static final String AES_ALGORITHM = "AES/CBC/PKCS5PADDING";
    private static final String KEY_HINT = "12345678901234567890123456789012";
    private static final String IV_HINT = "wpaycoremodule00";

    private final SecretKey secretKey;
    private final IvParameterSpec iv;

    private CryptoAES256(){
        this.secretKey = new SecretKeySpec(KEY_HINT.getBytes(StandardCharsets.UTF_8), "AES");
        this.iv = new IvParameterSpec(IV_HINT.getBytes(StandardCharsets.UTF_8));
    }

    public String encrypt(String plainText) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(AES_ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.iv);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        int blockSize = 128; //block size
        byte[] dataBytes = plainText.getBytes(StandardCharsets.UTF_8);

        int plaintextLength = dataBytes.length;
        int fillChar = ((blockSize - (plaintextLength % blockSize)));
        plaintextLength += fillChar; //pad

        byte[] plaintext = new byte[plaintextLength];
        Arrays.fill(plaintext, (byte) fillChar);
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

        //encrypt
        byte[] cipherBytes;
        try {
            cipherBytes = cipher.doFinal(plaintext);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        //add iv to front of cipherBytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        try {
            outputStream.write(iv.getIV());
            outputStream.write( cipherBytes );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //encode into base64
        byte [] encryptedIvText = outputStream.toByteArray();
        return new String(Base64.getEncoder().encode(encryptedIvText), StandardCharsets.UTF_8);
    }

    public String decrypt(String encText) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(AES_ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.iv);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        //decode with base64 decoder
        byte [] encryptedIvTextBytes = Base64.getDecoder().decode(encText);

        // Extract IV.
        System.arraycopy(encryptedIvTextBytes, 0, new byte[16], 0, 16);

        // Extract encrypted part.
        int encryptedSize = encryptedIvTextBytes.length - 16;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedIvTextBytes, 16, encryptedBytes, 0, encryptedSize);

        byte[] aesdecode;
        try {
            aesdecode = cipher.doFinal(encryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        byte[] origin = new byte[aesdecode.length - (aesdecode[aesdecode.length - 1])];
        System.arraycopy(aesdecode, 0, origin, 0, origin.length);

        return new String(origin, StandardCharsets.UTF_8);
    }
}
