package com.lawencon.elearning.model;

/**
 *  @author Dzaky Fadhilla Guci
 */

public enum GeneralCode {

  TEACHER_EXAM("GC-TE"), REGISTER_COURSE("GC-RC"), RESET_PASSWORD("GC-RP");
  
  private String code;
  
  GeneralCode(String code) {
    this.code = code;
  }
  
  public String getCode() {
    return code;
  }
}
