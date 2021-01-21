package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.Exam;
import com.lawencon.util.Callback;

public interface ExamDao {

  List<Exam> getAllExams() throws Exception;

  void saveExam(Exam data, Callback before) throws Exception;

  Exam findExamById(String id) throws Exception;
}
