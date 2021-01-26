package com.lawencon.elearning.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.DetailExamDao;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.service.DetailExamService;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */
@Service
public class DetailExamServiceImpl extends BaseServiceImpl implements DetailExamService {
  @Autowired
  private DetailExamDao dtlExamDao;

  @Override
  public List<DetailExam> getListScoreAvg(String id) throws Exception {
    return dtlExamDao.getListScoreAvg(id);
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    begin();
    dtlExamDao.updateIsActive(id, userId);
    commit();
  }

  @Override
  public void insertDetailExam(DetailExam dtlExam, Callback before) throws Exception {
    dtlExamDao.insertDetailExam(dtlExam, null);
  }

  @Override
  public void updateDetailExam(DetailExam dtlExam, Callback before) throws Exception {
    setupUpdatedValue(dtlExam, () -> dtlExamDao.getDetailById(dtlExam.getId()));
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
