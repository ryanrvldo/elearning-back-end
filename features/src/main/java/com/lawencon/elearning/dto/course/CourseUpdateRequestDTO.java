package com.lawencon.elearning.dto.course;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseUpdateRequestDTO extends CourseCreateRequestDTO {

  @NotBlank
  private String id;
  @NotBlank
  private String updateBy;

  @NotBlank
  private String status;
}
