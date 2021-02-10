package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.dto.admin.DashboardStudentResponseDto;
import com.lawencon.elearning.dto.admin.RegisteredStudentMonthlyDto;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Student;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
public interface StudentDao {

  void insertStudent(Student data, Callback before) throws Exception;

  Student getStudentById(String id) throws Exception;

  Student getStudentProfile(String id) throws Exception;

  Student getStudentByIdUser(String id) throws Exception;

  void updateStudentProfile(Student data, Callback before) throws Exception;

  void deleteStudentById(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  Student getStudentDashboard(String id) throws Exception;

  List<Student> findAll() throws Exception;
  
  List<Student> getListStudentByIdCourse(String idCourse) throws Exception;

  Integer countTotalStudent() throws Exception;

  List<DetailExam> getStudentExamReport(String studentId) throws Exception;

  Integer countRecentlyRegisteredStudent() throws Exception;

  List<RegisteredStudentMonthlyDto> countTotalRegisteredStudent() throws Exception;

  DashboardStudentResponseDto countStudentData() throws Exception;

}
