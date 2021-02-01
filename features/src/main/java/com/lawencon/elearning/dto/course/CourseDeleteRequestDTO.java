package com.lawencon.elearning.dto.course;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseDeleteRequestDTO {

  @NotBlank
  private String id;
  @NotBlank
  private String updateBy;
}
