package com.lawencon.elearning.dto;

import java.time.LocalDateTime;
import com.lawencon.elearning.model.Gender;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
public class StudentDashboardDTO {

  private String id;
  private LocalDateTime createdAt;
  private String phone;
  private Gender gender;
  private String firstName;
  private String lastName;
  private String email;
  private String idPhoto;

}