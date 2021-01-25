package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.model.Teacher;

/**
 *  @author Dzaky Fadhilla Guci
 */

public interface TeacherService {

  List<Teacher> getAllTeachers() throws Exception;

  void saveTeacher(Teacher data) throws Exception;

  Teacher findTeacherById(String id) throws Exception;

  // Get Teacher By Id
  Teacher findTeacherByIdCustom(String id) throws Exception;

  void softDelete(String id) throws Exception;

  void updateTeacher(Teacher data) throws Exception;




}
