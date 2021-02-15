package com.lawencon.elearning.dto.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ScheduleResponseDTO {

  private String id;

  @JsonFormat(pattern = "dd MMM yyyy")
  private LocalDate date;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime startTime;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime endTime;
}
