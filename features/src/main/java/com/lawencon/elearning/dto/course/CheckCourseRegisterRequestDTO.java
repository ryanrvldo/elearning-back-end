package com.lawencon.elearning.dto.course;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CheckCourseRegisterRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  public String studentId;
  @NotBlank
  @Size(min = 32, max = 36)
  public String courseId;
}
