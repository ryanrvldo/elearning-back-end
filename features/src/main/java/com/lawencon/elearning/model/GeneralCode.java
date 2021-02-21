package com.lawencon.elearning.model;

/**
 * @author Dzaky Fadhilla Guci
 */

public enum GeneralCode {

  TEACHER_EXAM("GC-TE"),
  REGISTER_COURSE("GC-RC"),
  REGISTER_COURSE_SUCCESS("GC-RCS"),
  USER_REGISTRATION("GC-UR"),
  USER_REGISTER_CONFIRMATION_SUCCESS("GC-URC-SS"),
  USER_REGISTER_CONFIRMATION_FAILED("GC-URC-FD"),
  TOKEN_EXPIRED("GC-TKN-EXP"),
  TOKEN_GENERATED("GC-TKN-NEW"),
  RESET_PASSWORD("GC-RP");

  private final String code;

  GeneralCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
