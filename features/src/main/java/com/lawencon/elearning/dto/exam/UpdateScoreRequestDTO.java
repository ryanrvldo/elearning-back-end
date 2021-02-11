package com.lawencon.elearning.dto.exam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class UpdateScoreRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotNull
  @Min(0)
  @Max(100)
  private Double grade;

  @NotBlank
  @Size(min = 32, max = 36)
  private String updatedBy;

}
