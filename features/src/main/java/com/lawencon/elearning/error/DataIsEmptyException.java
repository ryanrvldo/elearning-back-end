package com.lawencon.elearning.error;

import lombok.NoArgsConstructor;

/**
 * @author Dzaky Fadhilla Guci
 */

@NoArgsConstructor
public class DataIsEmptyException extends Exception {

  private static final long serialVersionUID = -8823691403564558799L;

  public DataIsEmptyException(String paramValue) {
    super(String.format("%s data is empty.", paramValue));
  }

}
