package com.lawencon.elearning.model;

/**
 * @author : Galih Dika Permana
 *
**/

public enum CourseStatus {

  REGISTRATION("Registration"), ONGOING("Ongoing"), FINISHED("Finished");

  String status;

  CourseStatus(String status){
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

}