package com.lawencon.elearning.dto.student;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.elearning.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDashboardDTO {

  private String id;
  private String code;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private Gender gender;
  private String idPhoto;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
  private Boolean isActive;
}
