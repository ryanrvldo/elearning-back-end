package com.lawencon.elearning.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class StudentCourseVerifyRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime verifiedAt;

  @NotBlank
  @Size(min = 32, max = 36)
  private String updatedBy;

}
