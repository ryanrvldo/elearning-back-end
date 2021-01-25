package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.model.CourseCategory;

/**
 * @author : Galih Dika Permana
 */

public interface CourseCategoryService {

  List<CourseCategory> getListCourseCategory() throws Exception;

  void insertCourseCategory(CourseCategory courseCategory) throws Exception;

  void updateCourseCategory(CourseCategory courseCategory) throws Exception;

  void deleteCourseCategory(String id) throws Exception;

  void softDelete(String id) throws Exception;
}
