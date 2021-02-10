package com.lawencon.elearning.dto.subject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */

@Data
public class UpdateSubjectCategoryRequestDTO {

  @NotBlank
  private String id;

  @NotBlank
  @Size(max = 50)
  private String code;

  @NotBlank
  @Size(max = 100)
  private String subjectName;

  private String description;

  @NotBlank
  private String updatedBy;

}
