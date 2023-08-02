package com.wpay.common.global.annotation;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Crypto {

    Encodings encodings() default Encodings.UTF_8;

    Type type();

    Algorithm algorithm();

    CryptoKey cryptoKey();

    enum Algorithm {
        AES, SEED
    }

    enum CryptoKey {
        API, DB, MERCHANT, RANDOM, OTHER
    }
    
    @AllArgsConstructor
    enum Encodings {
        UTF_8(StandardCharsets.UTF_8),
        ISO_8859_1(StandardCharsets.ISO_8859_1),
        EUC_KR(Charset.forName("EUC-KR"));

        @Getter private final Charset charset;
    }
    
    enum Type {
        ENCRYPTION, DECRYPTION
    }
}
