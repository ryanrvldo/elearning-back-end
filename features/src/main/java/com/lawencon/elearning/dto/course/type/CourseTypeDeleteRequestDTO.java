package com.lawencon.elearning.dto.course.type;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseTypeDeleteRequestDTO {

  @NotBlank
  private String id;
  @NotBlank
  private String updateBy;
}
