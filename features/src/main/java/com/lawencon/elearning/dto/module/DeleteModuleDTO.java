package com.lawencon.elearning.dto.module;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class DeleteModuleDTO {

  @NotBlank
  private String id;
  @NotBlank
  private String updateBy;
}
