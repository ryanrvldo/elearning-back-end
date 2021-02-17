package com.lawencon.elearning.controller;

import com.lawencon.elearning.dto.exam.UpdateScoreRequestDTO;
import com.lawencon.elearning.service.ExamService;
import com.lawencon.elearning.util.WebResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author William
 */

@RestController
@RequestMapping("/exam")
public class ExamController {

  @Autowired
  private ExamService examService;

  @GetMapping("/average-scores/student/{id}")
  public ResponseEntity<?> getAverageScore(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(examService.getListScoreAvg(id), HttpStatus.OK);
  }

  @GetMapping("/module/{id}")
  public ResponseEntity<?> getDetailModuleExam(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(examService.getExamsByModule(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/submission")
  public ResponseEntity<?> getExamSubmission(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(examService.getExamSubmissions(id), HttpStatus.OK);
  }

  @GetMapping("/{examId}/submission/{studentId}")
  public ResponseEntity<?> getStudentExamSubmission(@PathVariable("examId") String examId,
      @PathVariable("studentId") String studentId) throws Exception {
    return WebResponseUtils
        .createWebResponse(examService.getStudentExamSubmission(examId, studentId), HttpStatus.OK);
  }

  @PostMapping("/student")
  public ResponseEntity<?> sendStudentExam(@RequestPart("file") MultipartFile multiPartFile,
      @RequestParam("examId") String examId, @RequestParam("studentId") String studentId)
      throws Exception {
    examService.submitAssignment(multiPartFile, examId, studentId);
    return WebResponseUtils.createWebResponse("Insert Success", HttpStatus.OK);
  }

  @PostMapping("/module")
  public ResponseEntity<?> sendTeacherExam(@RequestPart("file") MultipartFile multiPartFile,
      @RequestPart("body") String body) throws Exception {
    examService.saveExam(multiPartFile, body);
    return WebResponseUtils.createWebResponse("Insert Exam Success", HttpStatus.OK);
  }

  @PutMapping("/submission")
  public ResponseEntity<?> updateScore(@RequestBody UpdateScoreRequestDTO body) throws Exception {
    examService.updateScoreAssignment(body);
    return WebResponseUtils.createWebResponse("Update Success", HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<?> deleteExam(@PathVariable("id") String id) throws Exception {
    examService.deleteExam(id);
    return WebResponseUtils.createWebResponse("Update Success", HttpStatus.OK);
  }

  @DeleteMapping("submission/id/{id}")
  public ResponseEntity<?> deleteExamSubmission(@PathVariable("id") String id) throws Exception {
    examService.deleteExamSubmission(id);
    return WebResponseUtils.createWebResponse("Delete success", HttpStatus.OK);
  }

}
