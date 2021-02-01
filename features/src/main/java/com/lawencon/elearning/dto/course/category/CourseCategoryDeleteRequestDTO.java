package com.lawencon.elearning.dto.course.category;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseCategoryDeleteRequestDTO {

  @NotBlank
  private String id;
  @NotBlank
  private String updateBy;
}
