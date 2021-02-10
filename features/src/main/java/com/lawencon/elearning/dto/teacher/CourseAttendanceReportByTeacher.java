package com.lawencon.elearning.dto.teacher;

import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseAttendanceReportByTeacher {

  public String moduleName;
  public String date;
  public Integer absent;
  public Integer present;
  public Integer totalStudent;
}
