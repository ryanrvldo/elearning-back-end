package com.lawencon.elearning.dto;

import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class StudentListByCourseResponseDTO {

  private String studentCourseId;
  private String studentId;
  private String code;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String gender;
  private Boolean isVerified;
}
