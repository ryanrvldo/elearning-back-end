package com.lawencon.elearning.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.lawencon.elearning.dto.exam.ExamsModuleResponseDTO;
import com.lawencon.elearning.dto.exam.UpdateScoreRequestDTO;
import com.lawencon.elearning.dto.exam.detail.ScoreAverageResponseDTO;
import com.lawencon.elearning.dto.exam.detail.ScoreReportDTO;
import com.lawencon.elearning.dto.exam.detail.SubmissionStudentResponseDTO;
import com.lawencon.elearning.dto.exam.detail.SubmissionsByExamResponseDTO;
import com.lawencon.elearning.model.Exam;

/**
 * @author Dzaky Fadhilla Guci
 */

public interface ExamService {

  List<Exam> getAllExams() throws Exception;

  void saveExam(MultipartFile multiPartFile, String body) throws Exception;

  void updateExam(Exam data) throws Exception;

  Exam findExamById(String id) throws Exception;

  List<ExamsModuleResponseDTO> getExamsByModule(String id) throws Exception;

  Long getCountData() throws Exception;

  Long getCountDataByModule(String moduleId) throws Exception;

  String getIdByCode(String code) throws Exception;


  // ----------------------- Detail Exam ------------------------
  void submitAssignemt(MultipartFile multiPartFile, String examId, String studentId)
      throws Exception;

  void updateScoreAssignment(UpdateScoreRequestDTO data) throws Exception;

  List<ScoreAverageResponseDTO> getListScoreAvg(String studentId) throws Exception;

  List<SubmissionsByExamResponseDTO> getExamSubmissions(String examId) throws Exception;

  List<SubmissionStudentResponseDTO> getStudentExamSubmission(String examId, String studentId)
      throws Exception;

  List<ScoreReportDTO> getListScoreReport(String id) throws Exception;


}
