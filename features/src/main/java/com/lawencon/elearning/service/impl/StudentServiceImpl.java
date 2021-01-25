package com.lawencon.elearning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.StudentService;

/**
 * 
 * @author WILLIAM
 *
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl implements StudentService {

  @Autowired
  StudentDao studentDao;

  @Override
  public void insertStudent(Student data) throws Exception {
    studentDao.insertStudent(data, null);
  }

  @Override
  public Student getStudentById(String id) throws Exception {
    return studentDao.getStudentById(id);
  }

  @Override
  public Student getStudentProfile(String id) throws Exception {
    return studentDao.getStudentProfile(id);
  }

  @Override
  public void updateStudentProfile(Student data) throws Exception {
    studentDao.updateStudentProfile(data, null);
  }

  @Override
  public void deleteByStudentId(String id) throws Exception {
    studentDao.deleteStudentById(id);
  }

  @Override
  public void updateIsActiveById(Student data) throws Exception {
    data.setIsActive(false);
    studentDao.updateIsActiveById(data, null);
  }

  @Override
  public Student getStudentByIdUser(String id) throws Exception {
    return studentDao.getStudentByIdUser(id);
  }

}
