package com.lawencon.elearning.dto.module;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WILLIAM
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateModuleDTO extends ModuleRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotBlank
  @Size(min = 32, max = 36)
  private String updatedBy;

  @NotBlank
  @Size(min = 32, max = 36)
  private String idSchedule;

}
