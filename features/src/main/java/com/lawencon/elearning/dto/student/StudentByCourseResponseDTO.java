package com.lawencon.elearning.dto.student;

import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
public class StudentByCourseResponseDTO {

  private String id;
  private String code;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String gender;

}
