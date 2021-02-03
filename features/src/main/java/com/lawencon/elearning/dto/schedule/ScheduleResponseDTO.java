package com.lawencon.elearning.dto.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ScheduleResponseDTO {

  private String id;

  @JsonFormat(pattern = "dd LLL yyyy")
  private LocalDate date;

  @JsonFormat(pattern = "dd LLL yyyy HH:mm:ss")
  private LocalTime startTime;

  @JsonFormat(pattern = "dd LLL yyyy HH:mm:ss")
  private LocalTime endTime;
}
