package com.lawencon.elearning.dto.experience;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author Rian Rivaldo
 */
@Data
public class ExperienceCreateRequestDTO {

  @NotBlank
  @Size(max = 35)
  private String title;

  @NotBlank
  private String description;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  @PastOrPresent
  private LocalDate startDate;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @NotBlank
  @Size(min = 32, max = 36)
  private String teacherId;

  @NotBlank
  @Size(min = 32, max = 36)
  private String userId;

}
