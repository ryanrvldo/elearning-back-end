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
public class CreateSubjectCategoryRequestDTO {

  @NotBlank
  @Size(max = 50)
  private String code;

  private String description;

  @NotBlank
  @Size(max = 100)
  private String subjectName;

  @NotBlank
  private String createdBy;

}
