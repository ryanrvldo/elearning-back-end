package com.lawencon.elearning.service;

import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Student;

/**
 * 
 * @author WILLIAM
 *
 */
public interface StudentService {

  void insertStudent(Student data) throws Exception;

  DetailExam getStudentScores(String id) throws Exception;

  Student getStudentById(String id) throws Exception;

  Student getStudentProfile(String id) throws Exception;

  void updateStudentProfile(Student data) throws Exception;

  void deleteById(String id) throws Exception;

  void updateIsActive(Student data) throws Exception;

  Student getStudentByIdUser(String id) throws Exception;

}
