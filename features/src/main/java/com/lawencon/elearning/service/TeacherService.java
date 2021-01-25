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

  Teacher findTeacherByIdCustom(String id) throws Exception;

  void updateInActive(String id) throws Exception;

  void updateTeacher(Teacher data) throws Exception;

  Teacher getFullNameByUserId(String userId) throws Exception;

  void deleteTeacherById(String id) throws Exception;




}
