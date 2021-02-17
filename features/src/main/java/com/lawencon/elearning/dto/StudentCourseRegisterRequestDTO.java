package com.lawencon.elearning.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class StudentCourseRegisterRequestDTO {

  @NotBlank
  private String createdBy;

  @NotBlank
  @Size(min = 32, max = 36)
  private String courseId;

  @NotBlank
  @Size(min = 32, max = 36)
  private String studentId;
}
