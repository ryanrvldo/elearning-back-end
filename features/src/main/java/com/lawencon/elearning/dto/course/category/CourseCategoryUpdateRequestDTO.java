package com.lawencon.elearning.dto.course.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseCategoryUpdateRequestDTO extends CourseCategoryCreateRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotBlank
  private String updatedBy;

}
