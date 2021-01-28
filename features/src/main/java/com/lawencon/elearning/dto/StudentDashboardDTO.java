package com.lawencon.elearning.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.lawencon.elearning.model.Gender;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
public class StudentDashboardDTO {

  @NotBlank
  private String id;
  @NotNull
  private LocalDateTime createdAt;
  @NotBlank
  private String phone;
  @NotNull
  private Gender gender;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @NotBlank
  private String email;
  @NotBlank
  private String idPhoto;

}
