package com.lawencon.elearning.dto.course;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.elearning.dto.module.ModuleListReponseDTO;
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
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodStart;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodEnd;
  private List<ModuleListReponseDTO> modules;
}
