package com.lawencon.elearning.dto.course;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class UpdateStatusRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotNull
  private String status;

  @NotBlank
  @Size(min = 32, max = 36)
  private String updatedBy;

}
