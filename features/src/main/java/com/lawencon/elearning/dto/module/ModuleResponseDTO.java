package com.lawencon.elearning.dto.module;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ModuleResponseDTO {

  private String id;
  private String code;
  private String tittle;
  private String description;
  private String idSchedule;
  private LocalDate scheduleDate;
  private LocalTime startTime;
  private LocalTime endTime;
  private String subjectName;

}
