package com.lawencon.elearning.dto.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ScheduleResponseDTO {

  private String id;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
}
