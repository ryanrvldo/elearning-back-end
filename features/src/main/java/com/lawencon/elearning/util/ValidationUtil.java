package com.lawencon.elearning.util;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import com.lawencon.elearning.error.IllegalRequestException;

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

  public void validateUUID(String... uuids) throws IllegalRequestException {
    for (String uuid : uuids) {
      if (uuid == null || uuid.trim().isEmpty() || uuid.length() < 32 || uuid.length() > 36) {
        throw new IllegalRequestException(String.format("Id : %s  is not valid UUID.", uuid));
      }
    }
  }

  public void validateCode(String code, @NonNull int max) throws IllegalRequestException {
    if (code == null || code.trim().isEmpty() || code.trim().length() < max) {
      throw new IllegalRequestException("code", code);
    }
  }

}
