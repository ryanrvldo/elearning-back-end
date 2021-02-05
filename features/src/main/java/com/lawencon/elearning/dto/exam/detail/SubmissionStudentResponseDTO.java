package com.lawencon.elearning.dto.exam.detail;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class SubmissionStudentResponseDTO {

  private String detailId;
  private String fileId;
  private String fileName;
  private String code;
  private String firstName;
  private String lastName;
  private Double grade;
  @JsonFormat(pattern = "dd LLL yyyy")
  private LocalDate submittedDate;
}
