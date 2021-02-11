package com.lawencon.elearning.dto.module;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.lawencon.elearning.dto.ScheduleRequestDTO;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ModulRequestDTO {

  @NotBlank
  private String moduleCode;

  @NotBlank
  @Size(min = 32, max = 36)
  private String moduleCreatedBy;
  @NotBlank
  private String moduleTittle;
  @NotBlank
  private String moduleDescription;

  @NotBlank
  @Size(min = 32, max = 36)
  private String courseId;

  @NotBlank
  @Size(min = 32, max = 36)
  private String subjectId;

  @NotNull
  private ScheduleRequestDTO scheduleRequestDTO;
}
