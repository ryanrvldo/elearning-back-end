package com.lawencon.elearning.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Rian Rivaldo
 */
@Component
public class EncoderUtils {

  public BCryptPasswordEncoder getEncoder() {
    return new BCryptPasswordEncoder();
  }

  public String getHashPassword(String password) {
    return getEncoder().encode(password);
  }

}
