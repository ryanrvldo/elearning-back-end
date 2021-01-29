package com.lawencon.elearning.dto.student;

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
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private Gender gender;
  private String idPhoto;
  private LocalDateTime createdAt;

}
