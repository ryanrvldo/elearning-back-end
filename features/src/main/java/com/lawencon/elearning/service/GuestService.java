package com.lawencon.elearning.service;

import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.course.DashboardCourseResponseDTO;
import com.lawencon.elearning.dto.student.RegisterStudentDTO;
import com.lawencon.elearning.dto.teacher.TeacherForAdminDTO;
import java.util.List;

/**
 * @author Rian Rivaldo
 */
public interface GuestService {

  List<CourseResponseDTO> getCourses() throws Exception;

  DashboardCourseResponseDTO getCourseDetail(String courseId) throws Exception;

  List<TeacherForAdminDTO> getTeachers() throws Exception;

  void registerStudent(RegisterStudentDTO student) throws Exception;

  void forgotPassword(String email) throws Exception;

}
