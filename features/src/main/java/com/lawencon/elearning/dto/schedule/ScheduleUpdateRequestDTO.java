package com.lawencon.elearning.dto.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */

@Data
public class ScheduleUpdateRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotNull
  @FutureOrPresent
  private LocalDate date;

  @NotNull
  @JsonFormat(pattern = "hh:mm a")
  private LocalTime startTime;

  @NotNull
  @JsonFormat(pattern = "hh:mm a")
  private LocalTime endTime;


}
