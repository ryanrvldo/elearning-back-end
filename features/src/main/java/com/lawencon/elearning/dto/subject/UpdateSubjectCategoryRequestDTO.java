package com.lawencon.elearning.dto.subject;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author WILLIAM
 *
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateSubjectCategoryRequestDTO extends CreateSubjectCategoryRequestDTO {

  @NotBlank
  private String id;

}
