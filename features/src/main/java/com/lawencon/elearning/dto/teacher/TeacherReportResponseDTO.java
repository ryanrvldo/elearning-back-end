package com.lawencon.elearning.dto.teacher;

import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class TeacherReportResponseDTO {

  private String studentFirstName;
  private String studentLastName;
  private Integer totalExam;
  private Integer totalAssignment;
  private Integer notAssignment;
  private Double avgScore;
}
