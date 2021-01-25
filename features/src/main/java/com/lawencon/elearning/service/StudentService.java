package com.lawencon.elearning.service;

import com.lawencon.elearning.model.Student;

/**
 * 
 * @author WILLIAM
 *
 */
public interface StudentService {

  void insertStudent(Student data) throws Exception;

  Student getStudentById(String id) throws Exception;

  Student getStudentProfile(String id) throws Exception;

  void updateStudentProfile(Student data) throws Exception;

  void deleteByStudentId(String id) throws Exception;

  void updateIsActiveById(Student data) throws Exception;

  Student getStudentByIdUser(String id) throws Exception;

}
