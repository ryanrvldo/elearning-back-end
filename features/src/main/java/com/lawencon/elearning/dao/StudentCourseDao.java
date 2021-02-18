package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.dto.StudentListByCourseResponseDTO;
import com.lawencon.elearning.model.StudentCourse;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */

public interface StudentCourseDao {

  void registerCourse(StudentCourse studentCourse, Callback callBack)
      throws Exception;

  void verifyRegisterCourse(StudentCourse studentCourse, Callback callBack) throws Exception;

  StudentCourse getStudentCourseById(String id) throws Exception;

  List<StudentListByCourseResponseDTO> getListStudentCourseById(String courseId) throws Exception;

  StudentCourse checkVerifiedCourse(String studentId) throws Exception;
}
