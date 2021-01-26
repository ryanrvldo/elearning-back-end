package com.lawencon.elearning.util;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Rian Rivaldo
 */
@Component
public class ValidationUtil {

  @Autowired
  private Validator validator;

  public void validate(Object object) {
    Set<ConstraintViolation<Object>> result = validator.validate(object);
    if (result.size() != 0) {
      throw new ConstraintViolationException(result);
    }
  }

}
