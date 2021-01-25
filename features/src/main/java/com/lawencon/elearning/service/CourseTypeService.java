package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.model.CourseType;

/**
 * @author : Galih Dika Permana
 */

public interface CourseTypeService {

  List<CourseType> getListCourseType() throws Exception;

  void insertCourseType(CourseType courseType) throws Exception;

  void updateCourseType(CourseType courseType) throws Exception;

  void deleteCourseType(String id) throws Exception;

  void updateIsActived(String id) throws Exception;
}
