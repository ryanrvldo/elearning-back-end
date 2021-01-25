package com.lawencon.elearning.error;

import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@NoArgsConstructor
public class DataIsNotExistsException extends Exception {

  public DataIsNotExistsException(String paramName, String paramValue) {
    super(String.format("Data is not exits with %s : %s.", paramName, paramValue));
  }

}
