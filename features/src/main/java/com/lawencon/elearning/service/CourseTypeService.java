package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.course.type.CourseTypeCreateRequestDTO;
import com.lawencon.elearning.dto.course.type.CourseTypeResponseDTO;
import com.lawencon.elearning.dto.course.type.CourseTypeUpdateRequestDTO;

/**
 * @author : Galih Dika Permana
 */

public interface CourseTypeService {

  List<CourseTypeResponseDTO> getListCourseType() throws Exception;

  void insertCourseType(CourseTypeCreateRequestDTO courseType) throws Exception;

  void updateCourseType(CourseTypeUpdateRequestDTO courseType) throws Exception;

  void deleteCourseType(String id) throws Exception;

  void updateIsActive(UpdateIsActiveRequestDTO data) throws Exception;
}
