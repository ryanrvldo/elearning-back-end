package com.lawencon.elearning.controller;

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
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.student.RegisterStudentDTO;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * 
 * @author WILLIAM
 *
 */
@RestController
@RequestMapping("/student")
public class StudentController {

  @Autowired
  private StudentService studentService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getStudentProfile(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(studentService.getStudentProfile(id),
        HttpStatus.OK);
  }

  @GetMapping("/dashboard/{id}")
  public ResponseEntity<?> getStudentDashboard(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(studentService.getStudentDashboard(id),
        HttpStatus.OK);
  }

  @GetMapping("/{id}/course")
  public ResponseEntity<?> getStudentCourse(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(studentService.getStudentCourse(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> registerStudent(@RequestBody RegisterStudentDTO body) throws Exception {
    studentService.insertStudent(body);
    return WebResponseUtils.createWebResponse("Register Success", HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<?> updateStudentProfile(@RequestBody Student body) throws Exception {
    studentService.updateStudentProfile(body);
    return WebResponseUtils.createWebResponse("Update Profile Success", HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteStudent(@PathVariable("id") String id) throws Exception {
    studentService.deleteById(id);
    return WebResponseUtils.createWebResponse("Delete Success", HttpStatus.OK);
  }

}
