package com.lawencon.elearning.dto.course;

import java.time.LocalDateTime;
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

  @NotNull
  @FutureOrPresent
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodStart;

  @NotNull
  @FutureOrPresent
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodEnd;

  @NotBlank
  private String createdBy;

}
