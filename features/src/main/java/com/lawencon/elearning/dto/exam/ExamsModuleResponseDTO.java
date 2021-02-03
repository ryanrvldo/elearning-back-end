package com.lawencon.elearning.dto.exam;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.elearning.model.ExamType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamsModuleResponseDTO {

  private String id;
  private String code;
  private String description;
  private ExamType type;
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime startTime;
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime endTime;
  private String fileId;
  private String fileName;

}
