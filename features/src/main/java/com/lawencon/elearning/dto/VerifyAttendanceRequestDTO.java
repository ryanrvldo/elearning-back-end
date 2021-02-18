package com.lawencon.elearning.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */
@Data
public class VerifyAttendanceRequestDTO {

  @NotNull
  private List<String> idAttendance;

  @NotBlank
  @Size(min = 32, max = 36)
  private String moduleId;

  @NotBlank
  @Size(min = 32, max = 36)
  private String userId;

}
