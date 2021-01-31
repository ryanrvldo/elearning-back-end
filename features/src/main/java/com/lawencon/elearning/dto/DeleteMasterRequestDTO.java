package com.lawencon.elearning.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
public class DeleteMasterRequestDTO {

  @NotBlank
  private String id;

  @NotBlank
  private String updatedBy;

}
