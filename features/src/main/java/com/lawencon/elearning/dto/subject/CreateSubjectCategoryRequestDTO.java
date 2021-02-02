package com.lawencon.elearning.dto.subject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubjectCategoryRequestDTO {

  @NotBlank
  @Size(max = 50)
  private String code;

  @NotBlank
  @Size(max = 100)
  private String subjectName;

  private String description;

  @NotBlank
  private String createdBy;

}
