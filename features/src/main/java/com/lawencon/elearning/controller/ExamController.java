package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
  
   @GetMapping("average-scores/student/{id}")
   public ResponseEntity<?> getAverageScore(@PathVariable("id") String id) throws Exception {
     return WebResponseUtils.createWebResponse(examService.getListScoreAvg(id), HttpStatus.OK);
   }

   @GetMapping("module/{id}")
   public ResponseEntity<?> getDetailModuleExam(@PathVariable("id") String id) throws Exception {
     return WebResponseUtils.createWebResponse(examService.getExamsByModule(id), HttpStatus.OK);
   }

   // @PostMapping("/student")
   // public ResponseEntity<?> sendStudentExam(@RequestBody DetailExamDTO body) throws Exception {
   // // return WebResponseUtils.createWebResponse(examService, status)
   // return null;
   // }

}
