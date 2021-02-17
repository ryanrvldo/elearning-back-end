package com.lawencon.elearning.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.elearning.dto.module.ModuleListReponseDTO;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class DashboardCourseResponseDTO {

  private String id;
  private String code;
  private String name;
  private Integer capacity;
  private Integer totalStudent;
  private String description;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate periodStart;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate periodEnd;
  private List<ModuleListReponseDTO> modules;
}
