package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.student.RegisterStudentDTO;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import com.lawencon.elearning.dto.student.StudentDashboardDTO;
import com.lawencon.elearning.dto.student.StudentProfileDTO;
import com.lawencon.elearning.model.Student;

/**
 * 
 * @author WILLIAM
 *
 */
public interface StudentService {

  void insertStudent(RegisterStudentDTO data) throws Exception;

  Student getStudentById(String id) throws Exception;

  StudentProfileDTO getStudentProfile(String id) throws Exception;

  void updateStudentProfile(Student data) throws Exception;

  void deleteStudent(DeleteMasterRequestDTO data) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  Student getStudentByIdUser(String id) throws Exception;

  StudentDashboardDTO getStudentDashboard(String id) throws Exception;

  List<CourseResponseDTO> getStudentCourse(String id) throws Exception;

  List<StudentByCourseResponseDTO> getListStudentByIdCourse(String idCourse) throws Exception;

}
