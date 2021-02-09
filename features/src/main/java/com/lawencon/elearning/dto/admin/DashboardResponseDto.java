package com.lawencon.elearning.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDto {

  private DashboardCourseResponseDto course;

  private DashboardStudentResponseDto student;

  private DashboardTeacherResponseDto teacher;

}
