package com.lawencon.elearning.dto.exam.detail;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  @author Dzaky Fadhilla Guci
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionsByExamResponseDTO {

  private String id;
  private String code;
  private String firstName;
  private String lastName;
  private Double grade;
  private LocalDate submittedDate;

}
