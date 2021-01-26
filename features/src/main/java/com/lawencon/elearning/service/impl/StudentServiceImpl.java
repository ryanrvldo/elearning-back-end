package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.DetailExamService;
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

  @Autowired
  DetailExamService detailExamService;

  @Override
  public void insertStudent(Student data) throws Exception {
    data.setCreatedAt(LocalDateTime.now());
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
    setupUpdatedValue(data, () -> studentDao.getStudentById(data.getId()));
    studentDao.updateStudentProfile(data, null);
  }

  @Override
  public void deleteById(String id) throws Exception {
    studentDao.deleteStudentById(id);
  }

  @Override
  public void updateIsActive(Student data) throws Exception {
    data.setIsActive(false);
    studentDao.updateIsActive(data.getId(), data.getUser().getId());
  }

  @Override
  public Student getStudentByIdUser(String id) throws Exception {
    return studentDao.getStudentByIdUser(id);
  }

  @Override
  public DetailExam getStudentScores(String id) throws Exception {
    return detailExamService.getStudentScore(id);
  }

}
