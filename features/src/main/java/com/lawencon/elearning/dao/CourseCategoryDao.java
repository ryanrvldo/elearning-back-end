package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */

public interface CourseCategoryDao {

  List<CourseCategory> getListCourseCategory() throws Exception;

  void insertCourseCategory(CourseCategory courseCategory, Callback before) throws Exception;

  void updateCourseCategory(CourseCategory courseCategory, Callback before) throws Exception;

  void deleteCourseCategory(String id) throws Exception;

  void updateIsActive(String id, String updatedBy, boolean status) throws Exception;

  CourseCategory getCategoryById(String id) throws Exception;
}
