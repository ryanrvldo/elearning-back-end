package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.CourseResponseDTO;
import com.lawencon.elearning.dto.RegisterStudentDTO;
import com.lawencon.elearning.dto.StudentDashboardDTO;
import com.lawencon.elearning.dto.StudentProfileDTO;
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

  void deleteById(String id) throws Exception;

  void updateIsActive(Student data) throws Exception;

  Student getStudentByIdUser(String id) throws Exception;

  StudentDashboardDTO getStudentDashboard(String id) throws Exception;

  List<CourseResponseDTO> getStudentCourse(String id) throws Exception;

}
