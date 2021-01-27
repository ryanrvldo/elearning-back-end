package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.DetailExamDao;
import com.lawencon.elearning.dto.DetailExamDTO;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.DetailExamService;
import com.lawencon.elearning.util.TransactionNumberUtils;
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
  public List<DetailExam> getListScoreReport(String id) throws Exception {
    return dtlExamDao.getListScoreReport(id);
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
    begin();
    dtlExamDao.deleteDetailExam(id);
    commit();
  }

  @Override
  public void updateScoreStudent(String id, Double score) throws Exception {
    begin();
    dtlExamDao.updateScoreStudent(id, score);
    commit();
  }

  @Override
  public List<DetailExam> getExamSubmission(String id) throws Exception {
    return dtlExamDao.getExamSubmission(id);
  }

  @Override
  public void sendStudentExam(DetailExamDTO data) throws Exception {
    File file = new File();
    file.setId(data.getFileId());
    file.setVersion(data.getFileVersion());
    Exam exam = new Exam();
    exam.setId(data.getExamId());
    exam.setVersion(data.getExamVersion());
    Student student = new Student();
    student.setId(data.getStudentId());
    student.setVersion(data.getStudentVersion());

    DetailExam dtlExam = new DetailExam();
    dtlExam.setStudent(student);
    dtlExam.setExam(exam);
    dtlExam.setFile(file);
    dtlExam.setCreatedAt(LocalDateTime.now());
    dtlExam.setCreatedBy(data.getCreatedBy());
    dtlExam.setTrxDate(LocalDate.now());
    dtlExam.setTrxNumber(TransactionNumberUtils.generateDtlExamTrxNumber());

    dtlExamDao.sendStudentExam(dtlExam);
  }



}
