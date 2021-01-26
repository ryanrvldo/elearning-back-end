package com.lawencon.elearning.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.util.WebResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author WILLIAM
 *
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {

  @Autowired
  StudentService studentService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getStudentProfile(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(studentService.getStudentProfile(id),
        HttpStatus.OK);
  }

  @GetMapping("/{id}/scores")
  public ResponseEntity<?> getScores(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(studentService.getStudentScores(id),
        HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerStudent(@RequestBody String body) throws Exception {
    Student std = new ObjectMapper().readValue(body, Student.class);
    studentService.insertStudent(std);
    return new ResponseEntity<>("Input Berhasil", HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<?> updateStudentProfile(@RequestBody String body) throws Exception {
    Student std = new ObjectMapper().readValue(body, Student.class);
    studentService.updateStudentProfile(std);
    return new ResponseEntity<>("Update Berhasil", HttpStatus.OK);
  }

}
