package com.lawencon.elearning.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
public class AttendanceRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String idStudent;

  @NotBlank
  @Size(min = 32, max = 36)
  private String idModule;

}
