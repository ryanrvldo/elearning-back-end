package com.lawencon.elearning.dto.student;

import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class StudentReportDTO {

  private String courseName;
  private String moduleName;
  private String examType;
  private String examTitle;
  private String dateExam;
  private Double grade;

}
