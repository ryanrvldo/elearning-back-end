package com.lawencon.elearning.error;

/**
 * @author Rian Rivaldo
 */
public class DataAlreadyExistException extends Exception {

  private static final long serialVersionUID = 3863474436970522328L;

  public DataAlreadyExistException(String paramName, String paramValue) {
    super(String.format("Data is already exits with %s : %s.", paramName, paramValue));
  }

  public DataAlreadyExistException(String message) {
    super(message);
  }

}
