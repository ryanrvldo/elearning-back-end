package com.lawencon.elearning.dto.course.category;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseCategoryUpdateRequestDTO extends CourseCategoryCreateRequestDTO {
  @NotBlank
  private String id;
  @NotBlank
  private String updateBy;
}
