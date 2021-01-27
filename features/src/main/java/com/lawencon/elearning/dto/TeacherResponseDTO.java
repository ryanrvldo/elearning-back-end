package com.lawencon.elearning.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.lawencon.elearning.model.Gender;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class TeacherResponseDTO {

  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @NotBlank
  private String email;
  @NotBlank
  private LocalDateTime createdAt;
  @NotNull
  private Gender gender;

  public TeacherResponseDTO(String firstName, String lastName, String email,
      LocalDateTime createdAt, Gender gender) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.createdAt = createdAt;
    this.gender = gender;
  }




}
