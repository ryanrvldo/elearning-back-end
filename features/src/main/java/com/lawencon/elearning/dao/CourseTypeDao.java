package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.CourseType;

/**
 * @author : Galih Dika Permana
 */

public interface CourseTypeDao {
  List<CourseType> getListCourseType() throws Exception;

  void insertCourseType(CourseType courseType) throws Exception;

  void updateCourseType(CourseType courseType) throws Exception;

  void deleteCourseType(String id) throws Exception;

  void softDelete(String id) throws Exception;
}
