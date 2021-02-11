package com.lawencon.elearning.dto.student;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
  private String firstName;

  @Size(max = 50)
  private String lastName;

  @NotBlank
  @Size(max = 100)
  @Email
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

}
