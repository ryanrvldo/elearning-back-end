package com.lawencon.elearning.error;

/**
 * @author Rian Rivaldo
 */
public class IllegalRequestException extends Exception {

  public IllegalRequestException(String paramName, String paramValue) {
    super(String.format("Bad request with %s: %s. ", paramName, paramValue));
  }

}
