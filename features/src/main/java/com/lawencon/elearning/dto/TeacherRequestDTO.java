package com.lawencon.elearning.dto;

import com.lawencon.elearning.model.Gender;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequestDTO {

  @NotBlank
  private String code;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotBlank
  private String phone;

  @NotNull
  private Gender gender;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String email;

  @NotBlank
  private String roleId;

  @NotBlank
  private Long roleVersion;

  @NotBlank
  private String createdBy;

  @NotBlank
  private String titleDegree;



}
