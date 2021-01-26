package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ExamDao;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.service.ExamService;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Service
public class ExamServiceImpl extends BaseServiceImpl implements ExamService {

  @Autowired
  private ExamDao examDao;

  @Override
  public List<Exam> getAllExams() throws Exception {
    return examDao.getAllExams();
  }

  @Override
  public void saveExam(Exam data) throws Exception {
    data.setCreatedAt(LocalDateTime.now());
    examDao.saveExam(data, null);

  }

  @Override
  public void updateExam(Exam data) throws Exception {
    validateNullId(data.getId(), "Id");
    examDao.saveExam(data, null);
  }

  @Override
  public Exam findExamById(String id) throws Exception {
    validateNullId(id, "Id");
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException("id", id);
    }
    return examDao.findExamById(id);
  }

  @Override
  public List<Exam> getExamsByModule(String moduleId) throws Exception {
    validateNullId(moduleId, "Id Module");
    return examDao.getExamsByModule(moduleId);
  }

  @Override
  public Long getCountData() throws Exception {
    return examDao.getCountData();
  }

  @Override
  public Long getCountDataByModule(String id) throws Exception {
    validateNullId(id, "id");
    return examDao.getCountDataByModule(id);
  }

  @Override
  public String getIdByCode(String code) throws Exception {
    validateNullId(code, "code");
    return examDao.getIdByCode(code);
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
