package com.lawencon.elearning.dto.course;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseDashboardResponseDTO {

  @NotBlank
  private String id;
  @NotBlank
  private String code;
  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @NotNull
  private Integer capacity;
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodStart;
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodEnd;

}
