package com.lawencon.elearning.dao;

import java.util.List;
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

  List<Course> getCurentAvailableCourse() throws Exception;

  List<Course> getMyCourse() throws Exception;

  List<Course> getCourseForAdmin() throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  void registerCourse(Course course) throws Exception;

  Course getCourseById(String id) throws Exception;
}
