package com.lawencon.elearning.dto.course.type;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseTypeCreateRequestDTO {

  @NotBlank
  private String code;

  @NotBlank
  private String name;

  @NotBlank
  private String createdBy;

}
