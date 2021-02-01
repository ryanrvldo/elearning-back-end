package com.lawencon.elearning.service;

import java.util.List;
import java.util.Map;
import com.lawencon.elearning.dto.course.CourseCreateRequestDTO;
import com.lawencon.elearning.dto.course.CourseDeleteRequestDTO;
import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.course.CourseUpdateRequestDTO;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import com.lawencon.elearning.model.Course;

/**
 * @author : Galih Dika Permana
 */

public interface CourseService {

  List<Course> getListCourse() throws Exception;

  void insertCourse(CourseCreateRequestDTO courseDTO) throws Exception;

  void updateCourse(CourseUpdateRequestDTO courseDTO) throws Exception;

  void deleteCourse(CourseDeleteRequestDTO courseDTO) throws Exception;

  List<CourseResponseDTO> getCurrentAvailableCourse() throws Exception;

  List<CourseResponseDTO> getMyCourse(String id) throws Exception;

  List<CourseResponseDTO> getCourseForAdmin() throws Exception;

  Map<Course, Integer> getTeacherCourse(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  void registerCourse(String student, String course) throws Exception;

  List<ModuleResponseDTO> getDetailCourse(String id) throws Exception;

  List<StudentByCourseResponseDTO> getStudentByCourseId(String id) throws Exception;

}
