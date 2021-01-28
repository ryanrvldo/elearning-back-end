package com.lawencon.elearning.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.lawencon.elearning.dto.StudentExamDTO;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Exam;

/**
 *  @author Dzaky Fadhilla Guci
 */

public interface ExamService {

  List<Exam> getAllExams() throws Exception;

  void saveExam(MultipartFile multiPartFile, String content, String body) throws Exception;

  void updateExam(Exam data) throws Exception;

  Exam findExamById(String id) throws Exception;

  List<Exam> getExamsByModule(String id) throws Exception;

  Long getCountData() throws Exception;

  Long getCountDataByModule(String moduleId) throws Exception;

  String getIdByCode(String code) throws Exception;


  // ----------------------- Detail Exam ------------------------
  void submitAssignemt(StudentExamDTO data) throws Exception;

  void updateScoreAssignment(String id, Double newScore, String teacherId) throws Exception;

  List<DetailExam> getListScoreAvg(String studentId) throws Exception;

  List<DetailExam> getExamSubmissions(String examId) throws Exception;


}
