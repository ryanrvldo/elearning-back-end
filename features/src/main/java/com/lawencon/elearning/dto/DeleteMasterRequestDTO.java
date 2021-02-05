package com.lawencon.elearning.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
public class DeleteMasterRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotBlank
  @Size(min = 32, max = 36)
  private String updatedBy;

}
