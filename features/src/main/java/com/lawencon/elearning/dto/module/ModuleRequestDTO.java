package com.lawencon.elearning.dto.module;

import com.lawencon.elearning.dto.ScheduleRequestDTO;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ModuleRequestDTO {

  @NotBlank
  @Size(max = 50)
  private String code;

  @NotBlank
  private String title;

  @NotBlank
  private String description;

  @NotBlank
  @Size(min = 32, max = 36)
  private String courseId;

  @NotBlank
  @Size(min = 32, max = 36)
  private String subjectId;

  @NotNull
  private ScheduleRequestDTO schedule;

  @NotBlank
  @Size(min = 32, max = 36)
  private String createdBy;

}
