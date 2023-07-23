package com.wpay.common.global.dto;

import com.wpay.common.global.annotation.Crypto;
import com.wpay.common.global.crypto.CryptoAES256;
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
            if (Crypto.Algorithm.AES256.equals(crypto.algorithm())){
                try {
                    field.set(this, (Crypto.Type.ENCRYPTION.equals(crypto.type()))
                            ? CryptoAES256.getInstance().encrypt(value, crypto.encodings().getCharset())
                            : CryptoAES256.getInstance().decrypt(value, crypto.encodings().getCharset()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException("지원 하지 않는 암호알고리즘 입니다.");
            }
        }
    }

}
