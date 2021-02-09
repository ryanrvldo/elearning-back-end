package com.lawencon.elearning.dto.course.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseCategoryDeleteRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;
  @NotBlank
  private String updateBy;
}
