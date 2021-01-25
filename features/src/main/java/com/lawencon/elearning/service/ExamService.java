package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.model.Exam;

/**
 *  @author Dzaky Fadhilla Guci
 */

public interface ExamService {

  List<Exam> getAllExams() throws Exception;

  void saveExam(Exam data) throws Exception;

  void updateExam(Exam data) throws Exception;

  Exam findExamById(String id) throws Exception;

  List<Exam> getExamsByModule(String id) throws Exception;

  Long getCountData() throws Exception;

  Long getCountDataByModule(String moduleId) throws Exception;

  String getIdByCode(String code) throws Exception;

}
