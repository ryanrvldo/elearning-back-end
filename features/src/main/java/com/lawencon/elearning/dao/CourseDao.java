package com.lawencon.elearning.dao;

import java.util.List;
import java.util.Map;
import com.lawencon.elearning.dto.course.CheckCourseRegisterRequestDTO;
import com.lawencon.elearning.model.Course;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */

public interface CourseDao {

  List<Course> getListCourse() throws Exception;

  void insertCourse(Course course, Callback before) throws Exception;

  void updateCourse(Course course, Callback before) throws Exception;

  void deleteCourse(String id) throws Exception;

  List<Course> getCurrentAvailableCourse() throws Exception;

  List<Course> getCourseByStudentId(String id) throws Exception;

  List<Course> getCourseForAdmin() throws Exception;

  Map<Course, Integer[]> getTeacherCourse(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  void registerCourse(String course, String student) throws Exception;

  Course getCourseById(String id) throws Exception;

  Integer getTotalStudentByIdCourse(String id) throws Exception;

  Integer getCapacityCourse(String courseId) throws Exception;

  List<CheckCourseRegisterRequestDTO> checkDataRegisterCourse(String courseId) throws Exception;
}
