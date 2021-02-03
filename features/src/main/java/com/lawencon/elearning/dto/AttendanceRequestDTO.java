package com.lawencon.elearning.dto;

import javax.validation.constraints.NotBlank;
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

}
