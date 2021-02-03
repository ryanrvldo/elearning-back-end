package com.lawencon.elearning.dto.module;

import com.lawencon.elearning.dto.schedule.ScheduleResponseDTO;
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
  private String subjectName;
  private String attendanceId;
  private boolean isVerifyStatus;
  private ScheduleResponseDTO schedule;

}
