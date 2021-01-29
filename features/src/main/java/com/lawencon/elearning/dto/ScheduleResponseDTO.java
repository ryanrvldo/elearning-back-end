package com.lawencon.elearning.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  @author Dzaky Fadhilla Guci
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponseDTO {

  private String id;
  private Long version;
  private String code;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;

}
