package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.Course;

/**
 * @author : Galih Dika Permana
 */

public interface CourseDao {

  List<Course> getListCourse() throws Exception;

  void insertCourse(Course course) throws Exception;

  void updateCourse(Course course) throws Exception;

  void deleteCourse(String id) throws Exception;
}
