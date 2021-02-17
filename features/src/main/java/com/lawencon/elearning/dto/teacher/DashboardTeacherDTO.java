package com.lawencon.elearning.dto.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
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
  private Integer totalModule;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate periodStart;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate periodEnd;
}
