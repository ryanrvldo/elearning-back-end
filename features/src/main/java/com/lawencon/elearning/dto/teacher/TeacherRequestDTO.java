package com.lawencon.elearning.dto.teacher;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.lawencon.elearning.model.Gender;
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
  @Size(max = 50)
  private String code;

  @NotBlank
  @Size(max = 50)
  private String firstName;

  @NotBlank
  private String lastName;

  @NotBlank
  @Size(max = 20)
  private String phone;

  @NotNull
  private Gender gender;

  @NotBlank
  @Size(max = 100)
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String email;

  @NotBlank
  private String roleId;

  @NotNull
  @Min(0)
  private Long roleVersion;

  @NotBlank
  private String createdBy;

  @NotBlank
  @Size(max = 50)
  private String titleDegree;



}
