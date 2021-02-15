package com.lawencon.elearning.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ScheduleRequestDTO {

  @NotNull
  @FutureOrPresent
  private LocalDate date;

  @NotNull
  @JsonFormat(pattern = "hh:mm:ss a")
  private LocalTime startTime;

  @NotNull
  @JsonFormat(pattern = "hh:mm:ss a")
  private LocalTime endTime;

  @NotBlank
  @Size(min = 32, max = 36)
  private String createdBy;

}
