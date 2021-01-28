package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.course.type.CourseTypeCreateRequestDTO;
import com.lawencon.elearning.dto.course.type.CourseTypeUpdateRequestDTO;
import com.lawencon.elearning.model.CourseType;

/**
 * @author : Galih Dika Permana
 */

public interface CourseTypeService {

  List<CourseType> getListCourseType() throws Exception;

  void insertCourseType(CourseTypeCreateRequestDTO courseType) throws Exception;

  void updateCourseType(CourseTypeUpdateRequestDTO courseType) throws Exception;

  void deleteCourseType(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;
}
