package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.TeacherDao;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.service.TeacherService;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Service
public class TeacherServiceImpl extends BaseServiceImpl implements TeacherService {

  @Autowired
  private TeacherDao teacherDao;

  @Override
  public void softDelete(String id) throws Exception {
    // check data dipake apa ngga
    // kalo ngga deletebyId dao
    // kalo iya => softdelete dao

    try {
      begin();
      teacherDao.softDelete(id);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
    }

  }

  @Override
  public List<Teacher> getAllTeachers() throws Exception {
    return teacherDao.getAllTeachers();
  }

  @Override
  public void saveTeacher(Teacher data) throws Exception {
    data.setCreatedAt(LocalDateTime.now());
    teacherDao.saveTeacher(data, null);
  }

  @Override
  public Teacher findTeacherById(String id) throws Exception {

    return teacherDao.findTeacherById(id);
  }


  @Override
  public Teacher findTeacherByIdCustom(String id) throws Exception {
    return teacherDao.findTeacherByIdCustom(id);
  }

  @Override
  public void updateTeacher(Teacher data) throws Exception {
    data.setUpdatedAt(LocalDateTime.now());
    teacherDao.saveTeacher(data, null);

  }

}
