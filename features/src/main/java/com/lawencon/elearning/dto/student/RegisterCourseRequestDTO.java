package com.lawencon.elearning.dto.student;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class RegisterCourseRequestDTO {
  @NotBlank
  private String StudentId;
  @NotBlank
  private String CourseId;

}
