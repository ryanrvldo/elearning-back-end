package com.lawencon.elearning.dto.course.type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseTypeUpdateRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotBlank
  private String code;

  @NotBlank
  private String name;

  @NotBlank
  private String updatedBy;

}
