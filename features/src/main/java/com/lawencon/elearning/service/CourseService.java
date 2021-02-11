package com.lawencon.elearning.service;

import java.util.List;
import java.util.Map;
import com.lawencon.elearning.dto.admin.DashboardCourseResponseDto;
import com.lawencon.elearning.dto.course.CourseAdminResponseDTO;
import com.lawencon.elearning.dto.course.CourseCreateRequestDTO;
import com.lawencon.elearning.dto.course.CourseDeleteRequestDTO;
import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.course.CourseUpdateRequestDTO;
import com.lawencon.elearning.dto.course.DashboardCourseResponseDTO;
import com.lawencon.elearning.dto.course.DetailCourseResponseDTO;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Teacher;

/**
 * @author : Galih Dika Permana
 */

public interface CourseService {

  List<CourseResponseDTO> getAllCourse() throws Exception;

  void insertCourse(CourseCreateRequestDTO courseDTO) throws Exception;

  void updateCourse(CourseUpdateRequestDTO courseDTO) throws Exception;

  void deleteCourse(CourseDeleteRequestDTO courseDTO) throws Exception;

  List<CourseResponseDTO> getCurrentAvailableCourse(String studentId) throws Exception;

  List<CourseResponseDTO> getCourseByStudentId(String id) throws Exception;

  List<CourseAdminResponseDTO> getCourseForAdmin() throws Exception;

  Map<Course, Integer[]> getTeacherCourse(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  void registerCourse(String student, String course) throws Exception;

  DetailCourseResponseDTO getDetailCourse(String courseId, String studentId) throws Exception;

  List<StudentByCourseResponseDTO> getStudentByCourseId(String id) throws Exception;

  Course getCourseById(String id) throws Exception;

  DashboardCourseResponseDto dashboardCourseByAdmin() throws Exception;

  List<CourseAttendanceReportByTeacher> getCourseAttendanceReport(String courseId) throws Exception;

  Teacher getTeacherById(String teacherId) throws Exception;

  Integer getRegisterStudent() throws Exception;

  DashboardCourseResponseDTO getCourseForDashboard(String courseId) throws Exception;
}
