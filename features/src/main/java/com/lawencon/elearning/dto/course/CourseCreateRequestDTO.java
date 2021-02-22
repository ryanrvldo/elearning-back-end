package com.lawencon.elearning.dto.course;

import java.time.LocalDate;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
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

  @JsonFormat(pattern = "yyyy-MM-dd")
  @NotNull
  @FutureOrPresent
  private LocalDate periodStart;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @NotNull
  @FutureOrPresent
  private LocalDate periodEnd;

  @NotBlank
  private String createdBy;

}
