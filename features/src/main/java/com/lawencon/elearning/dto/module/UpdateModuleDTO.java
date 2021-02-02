package com.lawencon.elearning.dto.module;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author WILLIAM
 *
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateModuleDTO extends ModulRequestDTO {

  @NotBlank
  private String id;

  @NotBlank
  private String updatedBy;

  @NotBlank
  private String idSchedule;

  @NotNull
  @Min(0)
  private Long moduleVersion;

  @NotNull
  @Min(0)
  private Long scheduleVersion;

}
