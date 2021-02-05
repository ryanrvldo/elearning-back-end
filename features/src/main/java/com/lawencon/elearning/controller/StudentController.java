package com.lawencon.elearning.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.student.RegisterStudentDTO;
import com.lawencon.elearning.dto.student.StudentReportDTO;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.util.WebResponseUtils;
import com.lawencon.util.JasperUtil;

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

  @DeleteMapping
  public ResponseEntity<?> deleteStudent(@RequestBody DeleteMasterRequestDTO body)
      throws Exception {
    studentService.deleteStudent(body);
    return WebResponseUtils.createWebResponse("Delete Success", HttpStatus.OK);
  }

  @PostMapping("register/{student}/{course}")
  public ResponseEntity<?> registerCourse(@PathVariable("student") String student,
      @PathVariable("course") String course) throws Exception {
    studentService.RegisterCourse(student, course);
    return WebResponseUtils.createWebResponse("Success Register Course", HttpStatus.CREATED);
  }

  @GetMapping("report/{id}")
  public ResponseEntity<?> getStudentReport(@PathVariable("id") String studentId) throws Exception {
    List<StudentReportDTO> listData = new ArrayList<>();
    Student student = studentService.getStudentById(studentId);
    Map<String, Object> mapStudent = new HashMap<>();
    mapStudent.put("modelStudentFName", student.getUser().getFirstName());
    mapStudent.put("modelStudentLName", student.getUser().getLastName());
    mapStudent.put("modelStudentEmail", student.getUser().getEmail());
    mapStudent.put("modelStudentGender", student.getGender().toString());
    mapStudent.put("modelStudentPhone", student.getPhone());
    byte[] out;
    try {
      listData = studentService.getStudentExamReport(studentId);
      out = JasperUtil.responseToByteArray(listData, "StudentReports", mapStudent);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_PDF);
    return ResponseEntity.ok().headers(header).body(new ByteArrayResource(out));
  }



}
