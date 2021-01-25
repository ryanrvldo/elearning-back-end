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

  Teacher findTeacherByIdCustom(String id) throws Exception;

  void updateIsActive(String id) throws Exception;

  void updateTeacher(Teacher data, Callback before) throws Exception;

  Teacher findByUserId(String userId) throws Exception;

  void deleteTeacherById(String id) throws Exception;


}
