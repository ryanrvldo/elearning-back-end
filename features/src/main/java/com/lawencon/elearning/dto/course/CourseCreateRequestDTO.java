package com.lawencon.elearning.dto.course;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseCreateRequestDTO {

  @NotBlank
  private String code;

  @NotBlank
  private String description;

  @NotBlank
  @Size(min = 32, max = 36)
  private String courseTypeId;

  @NotBlank
  @Size(min = 32, max = 36)
  private String teacherId;

  @NotBlank
  private String courseCategoryId;

  @NotNull
  private Integer capacity;

  @NotNull
  private LocalDateTime periodStart;

  @NotNull
  private LocalDateTime periodEnd;

  @NotBlank
  private String createdBy;

}
