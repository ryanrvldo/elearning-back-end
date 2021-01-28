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

  private String firstName;

  private String lastName;

  private String email;

  private Gender gender;

  private LocalDateTime createdAt;

}
