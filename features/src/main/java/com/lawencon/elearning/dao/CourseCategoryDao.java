package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.CourseCategory;

/**
 * @author : Galih Dika Permana
 */

public interface CourseCategoryDao {

  List<CourseCategory> getListCourseCategory() throws Exception;

  void insertCourseCategory(CourseCategory courseCategory) throws Exception;

  void updateCourseCategory(CourseCategory courseCategory) throws Exception;

  void deleteCourseCategory(String id) throws Exception;
}
