package com.lawencon.elearning.dto.teacher;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class DeleteTeacherDTO {

  @NotBlank
  private String id;
  @NotBlank
  private String updatedBy;

}
