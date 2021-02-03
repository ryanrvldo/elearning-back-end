package com.lawencon.elearning.dto.exam.detail;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  @author Dzaky Fadhilla Guci
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreAverageResponseDTO {

  private Double averageScore;

  private String code;

  private String title;

  @JsonFormat(pattern = "yyyy LLL dd HH:mm:ss")
  private LocalDateTime startTime;

  @JsonFormat(pattern = "yyyy LLL dd HH:mm:ss")
  private LocalDateTime endTime;


}
