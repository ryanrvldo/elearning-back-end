package com.lawencon.elearning.dto.exam.detail;

import java.time.LocalDateTime;
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

  private LocalDateTime startTime;

  private LocalDateTime endTime;


}
