package com.lawencon.elearning.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class StudentExamDTO {

  @NotBlank
  private String examId;

  @NotNull
  @Min(0)
  private Long examVersion;

  @NotBlank
  private String studentId;

  @NotNull
  @Min(0)
  private Long studentVersion;

  @NotBlank
  private String createdBy;
}