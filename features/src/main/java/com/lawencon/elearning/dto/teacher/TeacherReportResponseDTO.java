package com.lawencon.elearning.dto.teacher;

import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class TeacherReportResponseDTO {

  private String studentCode;
  private String studentFirstName;
  private String studentLastName;
  private Integer totalUjian;
  private Integer totalAssignment;
  private Integer notAssignment;
  private Integer avgScore;
}
