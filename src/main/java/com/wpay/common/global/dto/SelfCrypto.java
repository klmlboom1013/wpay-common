package com.wpay.common.global.dto;

import com.wpay.common.global.annotation.Crypto;
import com.wpay.common.global.crypto.CryptoAES;
import com.wpay.common.global.crypto.CryptoSEED;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.Objects;

@Log4j2
public abstract class SelfCrypto {

    public void resetFieldDataCrypto () {
        final Class<?> clazz = this.getClass();
        for(Field field : clazz.getDeclaredFields()) {
            Crypto crypto = field.getAnnotation(Crypto.class);
            if(Objects.isNull(crypto)) continue;
            field.setAccessible(true);
            String value;
            try {
                value = (String) field.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            String result;
            if (Crypto.Algorithm.AES.equals(crypto.algorithm()) && Crypto.CryptoKey.API.equals(crypto.cryptoKey()))
            {
                try {
                    field.set(this, (Crypto.Type.ENCRYPTION.equals(crypto.type()))
                            ? CryptoAES.getInstance().apiEncrypt(value, crypto.encodings().getCharset())
                            : CryptoAES.getInstance().apiDecrypt(value, crypto.encodings().getCharset()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else if (Crypto.Algorithm.SEED.equals(crypto.algorithm()) && Crypto.CryptoKey.DB.equals(crypto.cryptoKey()))
            {
                try {
                    field.set(this, (Crypto.Type.ENCRYPTION.equals(crypto.type()))
                            ? CryptoSEED.getInstance().dbEncrypt(value, crypto.encodings().getCharset())
                            : CryptoSEED.getInstance().dbDecrypt(value, crypto.encodings().getCharset()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                throw new RuntimeException("필드 암호화 설정이 적용된 정책이 유효 하지 않습니다. 다시 확인 바랍니다.");
            }
        }
    }

    public void resetFieldDataCrypto (@NonNull String key, @NonNull String iv) {
        final Class<?> clazz = this.getClass();
        for(Field field : clazz.getDeclaredFields()) {
            Crypto crypto = field.getAnnotation(Crypto.class);
            if(Objects.isNull(crypto)) continue;
            field.setAccessible(true);
            String value;
            try {
                value = (String) field.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if(Boolean.FALSE.equals(
                    Crypto.CryptoKey.MERCHANT.equals(crypto.cryptoKey())
                            || Crypto.CryptoKey.RANDOM.equals(crypto.cryptoKey())
                            || Crypto.CryptoKey.OTHER.equals(crypto.cryptoKey()))) {
                throw new RuntimeException("필드 암호화 키 설정 정책이 유효 하지 않습니다. 다시 확인 바랍니다.");
            }

            String result;
            if (Crypto.Algorithm.AES.equals(crypto.algorithm()))
            {
                try {
                    field.set(this, (Crypto.Type.ENCRYPTION.equals(crypto.type()))
                            ? CryptoAES.getInstance().encrypt(value, crypto.encodings().getCharset(), key, iv)
                            : CryptoAES.getInstance().decrypt(value, crypto.encodings().getCharset(), key, iv));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else if (Crypto.Algorithm.SEED.equals(crypto.algorithm()))
            {
                try {
                    field.set(this, (Crypto.Type.ENCRYPTION.equals(crypto.type()))
                            ? CryptoSEED.getInstance().encrypt(value, crypto.encodings().getCharset(), key, iv)
                            : CryptoSEED.getInstance().decrypt(value, crypto.encodings().getCharset(), key, iv));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                throw new RuntimeException("필드 암호화 설정이 적용된 정책이 유효 하지 않습니다. 다시 확인 바랍니다.");
            }
        }
    }
}
