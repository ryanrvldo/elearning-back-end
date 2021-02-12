package com.lawencon.elearning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.elearning.dto.admin.DashboardCourseResponseDto;
import com.lawencon.elearning.dto.admin.DashboardResponseDto;
import com.lawencon.elearning.dto.admin.DashboardStudentResponseDto;
import com.lawencon.elearning.dto.admin.DashboardTeacherResponseDto;
import com.lawencon.elearning.service.AdminService;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.service.TeacherService;

/**
 * @author Rian Rivaldo
 */
@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private StudentService studentService;

  @Autowired
  private TeacherService teacherService;

  @Autowired
  private CourseService courseService;

  @Override
  public DashboardResponseDto getDashboard() throws Exception {
    DashboardStudentResponseDto studentResponse = studentService.getStudentDataForAdmin();
    DashboardCourseResponseDto courseResponse = courseService.dashboardCourseByAdmin();
    DashboardTeacherResponseDto teacherResponse = teacherService.getDashboardTeacher();
    return new DashboardResponseDto(courseResponse, studentResponse, teacherResponse);
  }

}
