package com.lawencon.elearning.dto.exam.detail;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dzaky Fadhilla Guci
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionsByExamResponseDTO {

  private String id;
  private String fileId;
  private String fileName;
  private String code;
  private String firstName;
  private String lastName;
  private Double grade;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate submittedDate;

}
