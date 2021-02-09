package com.lawencon.elearning.dto.course.type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseTypeUpdateRequestDTO extends CourseTypeCreateRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotBlank
  private String updateBy;

}
