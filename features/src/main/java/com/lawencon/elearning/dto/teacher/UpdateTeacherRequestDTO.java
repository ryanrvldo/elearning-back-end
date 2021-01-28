package com.lawencon.elearning.dto.teacher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.lawencon.elearning.model.Gender;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class UpdateTeacherRequestDTO {

  @NotBlank
  private String id;
  @NotBlank
  @Size(max = 50)
  private String firstName;
  @NotBlank
  @Size(max = 50)
  private String lastName;
  @NotBlank
  private String email;
  @NotBlank
  @Size(max = 50)
  private String titleDegree;
  @NotNull
  private Gender gender;
  @NotBlank
  private String updatedBy;

}
