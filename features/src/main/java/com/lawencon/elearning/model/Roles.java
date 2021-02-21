package com.lawencon.elearning.model;

/**
 * @author Rian Rivaldo
 */
public enum Roles {

  SUPER_ADMIN("RL-001"), ADMIN("RL-002"), TEACHER("RL-003"), STUDENT("RL-004");

  private final String code;

  Roles(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

}
