package com.lawencon.elearning.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
public class RegisterStudentDTO {

  @NotBlank
  @Size(max = 50)
  private String code;

  @NotBlank
  @Size(max = 50)
  private String firstName;

  @Size(max = 50)
  private String lastName;

  @NotBlank
  @Size(max = 100)
  private String email;

  @NotBlank
  @Size(max = 100)
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  @Size(max = 20)
  private String phone;

  @NotBlank
  private String gender;

  @NotBlank
  private String roleId;

  @NotNull
  private Long roleVersion;

  @NotBlank
  private String createdBy;

}
