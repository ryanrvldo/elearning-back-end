package com.lawencon.elearning.dto.exam.detail;

import java.time.LocalDateTime;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */
@Data
public class ScoreReportDTO {

  private String courseCode;
  private String courseDescription;
  private String moduleCode;
  private String moduleTitle;
  private LocalDateTime examStart;
  private LocalDateTime examEnd;
  private Double grade;


}
