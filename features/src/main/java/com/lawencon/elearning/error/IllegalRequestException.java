package com.lawencon.elearning.error;

/**
 * @author Rian Rivaldo
 */
public class IllegalRequestException extends Exception {

  private static final long serialVersionUID = -1812881037890558589L;

  public IllegalRequestException(String paramName, String paramValue) {
    super(String.format("Bad request with %s: %s. ", paramName, paramValue));
  }

  public IllegalRequestException(String message) {
    super(message);
  }

}
