package com.lawencon.elearning.error;

/**
 * @author Rian Rivaldo
 */
public class DataAlreadyExistException extends Exception {

  public DataAlreadyExistException(String paramName, String paramValue) {
    super(String.format("Data is already exits with %s : %s.", paramName, paramValue));
  }

  public DataAlreadyExistException(String message) {
    super(message);
  }

}
