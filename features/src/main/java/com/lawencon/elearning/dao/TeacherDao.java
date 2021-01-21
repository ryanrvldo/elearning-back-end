package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.util.Callback;

public interface TeacherDao {


  List<Teacher> getAllTeachers() throws Exception;

  void saveTeacher(Teacher data, Callback before) throws Exception;

  // Get Teacher By Id
  Teacher findTeacherById(String id) throws Exception;



}
