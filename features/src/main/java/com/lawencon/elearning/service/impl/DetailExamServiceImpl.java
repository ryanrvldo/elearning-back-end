package com.lawencon.elearning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.DetailExamDao;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.service.DetailExamService;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */

public class DetailExamServiceImpl extends BaseServiceImpl implements DetailExamService {
  @Autowired
  private DetailExamDao dtlExamDao;

  @Override
  public DetailExam getStudentScore(String id) throws Exception {
    return dtlExamDao.getStudentScore(id);
  }

  @Override
  public void updateIsActived(String id) throws Exception {
    begin();
    dtlExamDao.updateIsActived(id);
    commit();
  }

  @Override
  public void insertDetailExam(DetailExam dtlExam, Callback before) throws Exception {
    dtlExamDao.insertDetailExam(dtlExam, null);
  }

  @Override
  public void updateDetailExam(DetailExam dtlExam, Callback before) throws Exception {
    dtlExamDao.updateDetailExam(dtlExam, null);
  }

  @Override
  public void deleteDetailExam(String id) throws Exception {
    dtlExamDao.deleteDetailExam(id);
  }

  @Override
  public void updateScoreStudent(String id, Double score) throws Exception {
    dtlExamDao.updateScoreStudent(id, score);
  }

  @Override
  public DetailExam getExamSubmission(String id) throws Exception {
    return dtlExamDao.getExamSubmission(id);
  }

  @Override
  public void sendStudentExam(DetailExam dtlExam) throws Exception {
    dtlExamDao.sendStudentExam(dtlExam);
  }

}
