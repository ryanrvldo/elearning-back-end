package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.StudentDashboardDTO;
import com.lawencon.elearning.dto.StudentProfileDTO;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Student;

/**
 * 
 * @author WILLIAM
 *
 */
public interface StudentService {

  void insertStudent(Student data) throws Exception;

  Student getStudentById(String id) throws Exception;

  StudentProfileDTO getStudentProfile(String id) throws Exception;

  void updateStudentProfile(Student data) throws Exception;

  void deleteById(String id) throws Exception;

  void updateIsActive(Student data) throws Exception;

  Student getStudentByIdUser(String id) throws Exception;

  StudentDashboardDTO getStudentDashboard(String id);

  List<Course> getStudentCourse(String id) throws Exception;

}
