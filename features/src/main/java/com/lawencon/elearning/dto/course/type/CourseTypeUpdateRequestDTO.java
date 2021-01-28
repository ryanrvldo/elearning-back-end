package com.lawencon.elearning.dto.course.type;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseTypeUpdateRequestDTO extends CourseTypeCreateRequestDTO {

  @NotBlank
  private String id;

  @NotBlank
  private String updateBy;

}
