package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */

public interface CourseTypeDao {
  List<CourseType> getListCourseType() throws Exception;

  void insertCourseType(CourseType courseType, Callback before) throws Exception;

  void updateCourseType(CourseType courseType, Callback before) throws Exception;

  void deleteCourseType(String id) throws Exception;

  void setActiveFalse(String id, String updatedBy) throws Exception;

  void setActiveTrue(String id, String updatedBy) throws Exception;

  CourseType getTypeById(String id) throws Exception;
}
