package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */

public interface DetailExamService {

  List<DetailExam> getListScoreAvg(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  void insertDetailExam(DetailExam dtlExam, Callback before) throws Exception;

  void updateDetailExam(DetailExam dtlExam, Callback before) throws Exception;

  void deleteDetailExam(String id) throws Exception;

  void updateScoreStudent(String id, Double score) throws Exception;

  DetailExam getExamSubmission(String id) throws Exception;

  void sendStudentExam(DetailExam dtlExam) throws Exception;
}
