package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.DetailExam;

/**
 * @author : Galih Dika Permana
 */

public interface DetailExamDao {
  DetailExam getStudentScore(String id) throws Exception;

  void softDelete(String id) throws Exception;

  void insertDetailExam(DetailExam dtlExam) throws Exception;

  void updateDetailExam(DetailExam dtlExam) throws Exception;

  void deleteDetailExam(String id) throws Exception;

  void updateScoreStudent(String id, Double score) throws Exception;

  DetailExam getExamSubmission(String id) throws Exception;

  void sendStudentExam(DetailExam dtlExam) throws Exception;
}
