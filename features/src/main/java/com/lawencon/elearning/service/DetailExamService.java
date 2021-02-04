package com.lawencon.elearning.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.lawencon.elearning.dto.exam.detail.ScoreAverageResponseDTO;
import com.lawencon.elearning.dto.exam.detail.ScoreReportDTO;
import com.lawencon.elearning.dto.exam.detail.SubmissionsByExamResponseDTO;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */

public interface DetailExamService {

  List<ScoreAverageResponseDTO> getListScoreAvg(String id) throws Exception;

  List<ScoreReportDTO> getListScoreReport(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  void insertDetailExam(DetailExam dtlExam, Callback before) throws Exception;

  void updateDetailExam(DetailExam dtlExam, Callback before) throws Exception;

  void deleteDetailExam(String id) throws Exception;

  void updateScoreStudent(String id, Double score, String userId) throws Exception;

  List<SubmissionsByExamResponseDTO> getExamSubmission(String id) throws Exception;

  void sendStudentExam(MultipartFile multiPartFile, String examId, String studentId)
      throws Exception;
}
