package com.wpay.common.global.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Crypto {

    String charset() default "UTF-8";
    String algorithm() default "AES";
    boolean isEncrypt() default true;
}
