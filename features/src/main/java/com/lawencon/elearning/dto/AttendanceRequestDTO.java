package com.lawencon.elearning.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
public class AttendanceRequestDTO {

  @NotBlank
  private String idStudent;

  @NotBlank
  private String idModule;

  @NotNull
  @Min(0)
  private Long studentVersion;

  @NotNull
  @Min(0)
  private Long moduleVersion;

}
