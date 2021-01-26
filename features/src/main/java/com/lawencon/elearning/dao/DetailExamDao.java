package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */

public interface DetailExamDao {
  List<DetailExam> getListScoreAvg(String id) throws Exception;

  List<DetailExam> getListScoreReport(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  void insertDetailExam(DetailExam dtlExam, Callback before) throws Exception;

  void updateDetailExam(DetailExam dtlExam, Callback before) throws Exception;

  void deleteDetailExam(String id) throws Exception;

  void updateScoreStudent(String id, Double score) throws Exception;

  DetailExam getExamSubmission(String id) throws Exception;

  void sendStudentExam(DetailExam dtlExam) throws Exception;

  DetailExam getDetailById(String id) throws Exception;
}
