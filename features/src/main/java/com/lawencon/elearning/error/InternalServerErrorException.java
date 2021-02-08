package com.lawencon.elearning.error;

import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@NoArgsConstructor
public class InternalServerErrorException extends Exception {

  private static final long serialVersionUID = -171403067147528222L;

  public InternalServerErrorException(String message, Throwable cause) {
    super(message, cause);
  }

  public InternalServerErrorException(Throwable cause) {
    super(cause);
  }

}
