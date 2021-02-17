package com.lawencon.elearning.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseProgressResponseDTO {

  private String courseId;
  private String courseName;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate periodEnd;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate periodStart;
  private Integer totalModule;
  private Integer moduleComplete;
  private Double percentProgress;
}
