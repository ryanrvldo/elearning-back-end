package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.StudentCourseRegisterRequestDTO;
import com.lawencon.elearning.dto.StudentListByCourseResponseDTO;
import com.lawencon.elearning.model.StudentCourse;

/**
 * @author : Galih Dika Permana
 */

public interface StudentCourseService {

  void registerCourse(
      StudentCourseRegisterRequestDTO studentCourseRegister)
      throws Exception;

  void verifyRegisterCourse(String studentCourseId, String userId) throws Exception;

  List<StudentListByCourseResponseDTO> getListStudentCourseById(String courseId) throws Exception;

  StudentCourse getStudentCourseById(String id) throws Exception;

  Boolean checkVerifiedCourse(String studentId, String courseId) throws Exception;

}
