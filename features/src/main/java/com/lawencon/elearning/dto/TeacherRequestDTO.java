package com.lawencon.elearning.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.lawencon.elearning.model.Gender;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
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
