package com.lawencon.elearning.service.impl;

import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.course.DashboardCourseResponseDTO;
import com.lawencon.elearning.dto.student.RegisterStudentDTO;
import com.lawencon.elearning.dto.teacher.TeacherForAdminDTO;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.GuestService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.service.TeacherService;
import com.lawencon.elearning.service.UserService;
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

  @Autowired
  private StudentService studentService;

  @Autowired
  private UserService userService;

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

  @Override
  public void registerStudent(RegisterStudentDTO student) throws Exception {
    studentService.insertStudent(student);
  }

  @Override
  public void forgotPassword(String email) throws Exception {
    userService.resetPassword(email);
  }

}
