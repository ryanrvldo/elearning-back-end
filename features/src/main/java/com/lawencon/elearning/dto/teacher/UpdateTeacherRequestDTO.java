package com.lawencon.elearning.dto.teacher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.lawencon.elearning.model.Gender;
import lombok.Data;

/**
 * @author Dzaky Fadhilla Guci
 */

@Data
public class UpdateTeacherRequestDTO {

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

  @Size(max = 50)
  private String titleDegree;

  @NotNull
  private Gender gender;

  @NotBlank
  @Size(min = 32, max = 36)
  private String updatedBy;

}
