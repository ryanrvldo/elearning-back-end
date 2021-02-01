package com.lawencon.elearning.dto.exam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class UpdateScoreRequestDTO {
  @NotBlank
  private String id;
  @NotNull
  private Double grade;
  @NotBlank
  private String updatedBy;

}
