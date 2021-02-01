package com.lawencon.elearning.dto.course;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
  private String courseTypeId;
  @NotBlank
  private String teacherId;
  @NotBlank
  private String courseCategoryId;
  @NotNull
  private Integer capacity;
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodStart;
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodEnd;
  @NotBlank
  private String createdBy;
  @NotBlank
  private String status;
}
