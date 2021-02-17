package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.dto.exam.ExamsModuleResponseDTO;
import com.lawencon.elearning.model.Exam;
import com.lawencon.util.Callback;

/**
 * @author Dzaky Fadhilla Guci
 */

public interface ExamDao {

  List<Exam> getAllExams() throws Exception;

  void saveExam(Exam data, Callback before) throws Exception;

  void updateExam(Exam data, Callback before) throws Exception;

  Exam findExamById(String moduleId) throws Exception;

  List<ExamsModuleResponseDTO> getExamsByModule(String id) throws Exception;

  Long getCountData() throws Exception;

  Long getCountDataByModule(String id) throws Exception;

  String getIdByCode(String code) throws Exception;

  void deleteExam(String id) throws Exception;

  String getIdFileById(String id) throws Exception;
}
