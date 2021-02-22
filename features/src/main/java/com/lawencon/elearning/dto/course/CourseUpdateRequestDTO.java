package com.lawencon.elearning.dto.course;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseUpdateRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

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
  @Size(min = 32, max = 36)
  private String courseCategoryId;

  @NotNull
  @Min(0)
  private Integer capacity;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @NotNull
  private LocalDate periodStart;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @NotNull
  private LocalDate periodEnd;

  @NotBlank
  @Size(min = 32, max = 36)
  private String updateBy;

  @NotBlank
  private String status;

}
