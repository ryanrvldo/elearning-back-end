package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.course.category.CourseCategoryCreateRequestDTO;
import com.lawencon.elearning.dto.course.category.CourseCategoryUpdateRequestDTO;
import com.lawencon.elearning.model.CourseCategory;

/**
 * @author : Galih Dika Permana
 */

public interface CourseCategoryService {

  List<CourseCategory> getListCourseCategory() throws Exception;

  void insertCourseCategory(CourseCategoryCreateRequestDTO courseCategory) throws Exception;

  void updateCourseCategory(CourseCategoryUpdateRequestDTO courseCategory) throws Exception;

  void deleteCourseCategory(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;
}
