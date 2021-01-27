package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.StudentExamDTO;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.service.ExamService;
import com.lawencon.elearning.util.WebResponseUtils;

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

   @PostMapping("/student")
   public ResponseEntity<?> sendStudentExam(@RequestBody StudentExamDTO body) throws Exception {
     examService.submitAssignemt(body);
     return WebResponseUtils.createWebResponse("Insert Success", HttpStatus.OK);
   }

   @PostMapping("/module")
   public ResponseEntity<?> sendStudentExam(@RequestBody Exam body) throws Exception {
     examService.saveExam(body);
     return WebResponseUtils.createWebResponse("Insert Success", HttpStatus.OK);
   }

   @PatchMapping("/submission")
   public ResponseEntity<?> updateScore(@RequestBody DetailExam body) throws Exception {
     examService.updateScoreAssignment(body.getId(), body.getGrade(), body.getUpdatedBy());
     return WebResponseUtils.createWebResponse("Insert Success", HttpStatus.OK);
   }

}
