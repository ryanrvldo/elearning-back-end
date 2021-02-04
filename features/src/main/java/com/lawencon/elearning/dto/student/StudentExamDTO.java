package com.lawencon.elearning.dto.student;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class StudentExamDTO {

  @NotBlank
  private String examId;

  @NotBlank
  private String studentId;

}
