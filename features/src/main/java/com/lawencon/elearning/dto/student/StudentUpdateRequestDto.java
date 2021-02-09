package com.lawencon.elearning.dto.student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.lawencon.elearning.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUpdateRequestDto {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotBlank
  @Size(max = 100)
  private String username;

  @NotBlank
  @Size(max = 50)
  private String firstName;

  @Size(max = 50)
  private String lastName;

  @NotBlank
  @Size(max = 20)
  private String phone;

  @NotNull
  private Gender gender;

  @NotBlank
  @Size(min = 32, max = 36)
  private String updatedBy;

}
