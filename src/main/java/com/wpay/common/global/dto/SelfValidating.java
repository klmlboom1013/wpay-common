package com.wpay.common.global.dto;

import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Log4j2
public abstract class SelfValidating<T> extends SelfCrypto {

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
  public void validateSelf() {
    Set<ConstraintViolation<T>> violations = validator.validate((T) this);
    if (Boolean.FALSE.equals(violations.isEmpty()))
      throw new ConstraintViolationException(violations);
  }

  public void validateCryptoSelf() {
    this.resetFieldDataCrypto();
    log.debug("Decription DTO Result: [{}]", this.toString());
    this.validateSelf();
  }
}
