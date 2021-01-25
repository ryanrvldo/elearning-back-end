package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.Student;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
public interface StudentDao {

  void insertStudent(Student data, Callback before) throws Exception;

  Student getStudentById(String id);

  Student getStudentProfile(String id) throws Exception;

  Student getStudentByIdUser(String id) throws Exception;

  void updateStudentProfile(Student data, Callback before) throws Exception;

  void deleteStudentById(String id) throws Exception;

  void updateIsActiveById(Student data, Callback before) throws Exception;

}
