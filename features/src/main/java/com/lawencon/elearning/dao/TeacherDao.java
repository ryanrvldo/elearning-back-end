package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.util.Callback;

/**
 * @author Dzaky Fadhilla Guci
 */

public interface TeacherDao {


  List<Teacher> getAllTeachers() throws Exception;

  void saveTeacher(Teacher data, Callback before) throws Exception;

  Teacher findTeacherById(String id) throws Exception;

  // Get Teacher By Id
  Teacher findTeacherByIdCustom(String id) throws Exception;

  void softDelete(String id) throws Exception;

  void updateTeacher(Teacher data, Callback before) throws Exception;


}
