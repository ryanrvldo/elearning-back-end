package com.lawencon.elearning.service.impl;

import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.course.DashboardCourseResponseDTO;
import com.lawencon.elearning.dto.teacher.TeacherForAdminDTO;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.GuestService;
import com.lawencon.elearning.service.TeacherService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rian Rivaldo
 */
@Service
public class GuestServiceImpl implements GuestService {

  @Autowired
  private CourseService courseService;

  @Autowired
  private TeacherService teacherService;

  @Override
  public List<CourseResponseDTO> getCourses() throws Exception {
    return courseService.getAllCourse();
  }

  @Override
  public DashboardCourseResponseDTO getCourseDetail(String courseId) throws Exception {
    return courseService.getCourseForDashboard(courseId);
  }

  @Override
  public List<TeacherForAdminDTO> getTeachers() throws Exception {
    return teacherService.getAllTeachers();
  }

}
