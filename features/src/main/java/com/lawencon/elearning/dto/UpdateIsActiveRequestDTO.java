package com.lawencon.elearning.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
@NoArgsConstructor
public class UpdateIsActiveRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotBlank
  @Size(min = 32, max = 36)
  private String updatedBy;

  @NotNull
  private Boolean status;

}
