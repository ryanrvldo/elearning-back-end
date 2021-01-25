package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.TeacherDao;
import com.lawencon.elearning.error.IllegalRequestException;
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
  public void updateInActive(String id) throws Exception {
    // check data dipake apa ngga
    // kalo ngga deletebyId dao
    // kalo iya => softdelete dao

    try {
      begin();
      teacherDao.updateIsActive(id);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
    }

  }

  @Override
  public List<Teacher> getAllTeachers() throws Exception {
    List<Teacher> listTeachers = teacherDao.getAllTeachers();
    if (listTeachers == null) {
      throw new Exception("Teacher data has not been registered");
    }
    return teacherDao.getAllTeachers();
  }

  @Override
  public void saveTeacher(Teacher data) throws Exception {
    if (data.getId() != null) {
      throw new IllegalRequestException("id", data.getId());
    }
    data.setCreatedAt(LocalDateTime.now());
    teacherDao.saveTeacher(data, null);
  }

  @Override
  public Teacher findTeacherById(String id) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException("id", id);
    }
    return teacherDao.findTeacherById(id);
  }


  @Override
  public Teacher findTeacherByIdCustom(String id) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException("id", id);
    }
    return teacherDao.findTeacherByIdCustom(id);
  }

  @Override
  public void updateTeacher(Teacher data) throws Exception {
    if (data.getId() == null || data.getId().trim().isEmpty()) {
      throw new IllegalRequestException("id", data.getId());
    }
    teacherDao.updateTeacher(data, null);

  }

  @Override
  public Teacher getFullNameByUserId(String userId) throws Exception {
    if (userId == null || userId.trim().isEmpty()) {
      throw new IllegalRequestException("User Id", userId);
    }
    return teacherDao.findByUserId(userId);
  }

  @Override
  public void deleteTeacherById(String id) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException("id", id);
    }
    commit();
    teacherDao.deleteTeacherById(id);
    begin();
  }

}
