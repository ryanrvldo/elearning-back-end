package com.lawencon.elearning.dao;

import java.util.List;
import java.util.SortedMap;
import com.lawencon.elearning.dto.admin.DashboardCourseResponseDto;
import com.lawencon.elearning.dto.course.CourseProgressResponseDTO;
import com.lawencon.elearning.dto.course.UpdateStatusRequestDTO;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.model.Course;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */

public interface CourseDao {

  List<Course> findAll() throws Exception;

  void insertCourse(Course course, Callback before) throws Exception;

  void updateCourse(Course course, Callback before) throws Exception;

  void deleteCourse(String id) throws Exception;

  List<Course> getCurrentAvailableCourse() throws Exception;

  List<Course> getCourseByStudentId(String id) throws Exception;

  List<Course> getCourseForAdmin() throws Exception;

  SortedMap<Course, Integer[]> getTeacherCourse(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  void registerCourse(String course, String student) throws Exception;

  Course getCourseById(String id) throws Exception;

  Integer getTotalStudentByIdCourse(String id) throws Exception;

  Integer getCapacityCourse(String courseId) throws Exception;

  Integer checkDataRegisterCourse(String courseId, String studentId) throws Exception;

  DashboardCourseResponseDto dashboardCourseByAdmin() throws Exception;

  Integer getRegisterStudent() throws Exception;

  List<CourseAttendanceReportByTeacher> getCourseAttendanceReport(String courseId) throws Exception;

  List<CourseProgressResponseDTO> getCourseProgressByStudentId(String studentId) throws Exception;

  Integer getModuleCompleteByStudentId(String courseId, String studentId) throws Exception;

  void updateCoursesStatus(UpdateStatusRequestDTO data) throws Exception;

  Integer getStudentPresentOnModule(String moduleId);

}
