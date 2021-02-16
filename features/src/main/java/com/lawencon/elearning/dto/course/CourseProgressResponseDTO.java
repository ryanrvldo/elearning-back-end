package com.lawencon.elearning.dto.course;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseProgressResponseDTO {

  private String courseId;
  private String courseName;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodEnd;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodStart;
  private Integer totalModule;
  private Integer moduleComplete;
  private Double percentProgress;
}
