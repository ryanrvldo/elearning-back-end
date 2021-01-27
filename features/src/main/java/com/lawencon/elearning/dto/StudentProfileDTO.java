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
public class StudentProfileDTO {

  private LocalDateTime createdAt;
  private Gender gender;
  private String firstName;
  private String lastName;
  private String email;

}
