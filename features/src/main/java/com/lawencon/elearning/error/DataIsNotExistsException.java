package com.lawencon.elearning.error;

import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@NoArgsConstructor
public class DataIsNotExistsException extends Exception {

  private static final long serialVersionUID = -3553094878594644503L;

  public DataIsNotExistsException(String paramName, String paramValue) {
    super(String.format("Data is not exits with %s : %s.", paramName, paramValue));
  }

  public DataIsNotExistsException(String message) {
    super(message);
  }

}
