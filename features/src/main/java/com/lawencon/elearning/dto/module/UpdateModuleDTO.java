package com.lawencon.elearning.dto.module;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.lawencon.elearning.dto.schedule.ScheduleUpdateRequestDTO;
import lombok.Data;

/**
 * @author WILLIAM
 */

@Data
public class UpdateModuleDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotBlank
  @Size(min = 32, max = 36)
  private String updatedBy;

  @NotBlank
  private String code;

  @NotBlank
  private String tittle;

  @NotBlank
  private String description;

  @NotBlank
  @Size(min = 32, max = 36)
  private String courseId;

  @NotBlank
  @Size(min = 32, max = 36)
  private String subjectId;

  @NotNull
  private ScheduleUpdateRequestDTO schedule;

}
