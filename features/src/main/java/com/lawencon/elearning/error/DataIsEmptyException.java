package com.lawencon.elearning.error;

import lombok.NoArgsConstructor;

/**
 *  @author Dzaky Fadhilla Guci
 */

@NoArgsConstructor
public class DataIsEmptyException extends Exception {

  public DataIsEmptyException(String paramValue) {
    super(String.format("%s data is empty.", paramValue));
  }
  
}
