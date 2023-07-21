package com.wpay.common.global.dto;

import com.wpay.common.global.annotation.Crypto;
import com.wpay.common.global.crypto.CryptoAES256;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

@Log4j2
public abstract class SelfValidating<T> implements BaseValidation {

  private final Validator validator;

  public SelfValidating() {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
  }

  /**
   * Evaluates all Bean Validations on the attributes of this
   * instance.
   */
  @Override
  public void validateSelf() {
    Set<ConstraintViolation<T>> violations = validator.validate((T) this);
    if (Boolean.FALSE.equals(violations.isEmpty()))
      throw new ConstraintViolationException(violations);
  }

  public void resetFieldDataCrypto (Object o) {
    final Class<?> clazz = o.getClass();
    log.debug(">>> {} 암복호화 작업을 진행 합니다.", clazz.getSimpleName());

    for(Field field : clazz.getDeclaredFields()) {
      Crypto crypto = field.getAnnotation(Crypto.class);

      if(Objects.isNull(crypto)){
        log.debug(">>> {} Field는 Crypto Annotation이 없습니다.", field.getName());
        continue;
      }
      log.debug(">>> {} Field는 Crypto Annotation이 있습니다.", field.getName());

      field.setAccessible(true);
      String value;
      try {
        value = (String) field.get(this);
        log.debug(">>> {} Field 암복호화 전 : {}", field.getName(), value);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
      try {
        String result = (crypto.isEncrypt()) ? CryptoAES256.getInstance().encrypt(value)
                : CryptoAES256.getInstance().decrypt(value);
        log.debug(">>> {} Field 암복호화 후 : {}", field.getName(), result);
        field.set(o, result);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
