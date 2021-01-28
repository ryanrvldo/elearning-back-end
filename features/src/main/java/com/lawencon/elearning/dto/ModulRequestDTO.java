package com.lawencon.elearning.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ModulRequestDTO {

  @NotBlank
  private String moduleCode;
  @NotBlank
  private String moduleCreatedBy;
  @NotBlank
  private String moduleTittle;
  @NotBlank
  private String moduleDescription;

  @NotBlank
  private String courseId;
  @NotNull
  @Min(0)
  private Long courseVersion;

  @NotBlank
  private String subjectId;
  @NotNull
  @Min(0)
  private Long subjectVersion;

  @NotNull
  private ScheduleRequestDTO scheduleRequestDTO;
}
