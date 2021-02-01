package com.lawencon.elearning.dto.teacher;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class DashboardTeacherDTO {

  private String id;
  private String code;
  private String name;
  private String description;
  private Integer capacity;
  private Integer totalStudent;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodStart;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodEnd;
}
