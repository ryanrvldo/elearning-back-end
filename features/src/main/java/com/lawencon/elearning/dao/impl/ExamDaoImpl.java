package com.lawencon.elearning.dao.impl;

import java.util.List;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.ExamDao;
import com.lawencon.elearning.model.Exam;
import com.lawencon.util.Callback;

public class ExamDaoImpl extends BaseDaoImpl<Exam> implements ExamDao {

  @Override
  public List<Exam> getAllExams() throws Exception {
    return getAll();
  }

  @Override
  public void saveExam(Exam data, Callback before) throws Exception {
    save(data, before, null, true, true);

  }

  @Override
  public Exam findExamById(String id) throws Exception {
    return getById(id);
  }



}
