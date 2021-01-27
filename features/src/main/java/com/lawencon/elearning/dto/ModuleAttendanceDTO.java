package com.lawencon.elearning.dto;

import java.util.List;
import com.lawencon.elearning.model.Attendance;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ModuleAttendanceDTO {

  private ModuleResponseDTO moduleResponse;
  private List<Attendance> attendance;
}
