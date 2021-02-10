package com.lawencon.elearning.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.student.RegisterStudentDTO;
import com.lawencon.elearning.dto.student.StudentReportDTO;
import com.lawencon.elearning.dto.student.StudentUpdateRequestDto;
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
  public ResponseEntity<?> updateStudentProfile(@RequestBody StudentUpdateRequestDto body)
      throws Exception {
    studentService.updateStudentProfile(body);
    return WebResponseUtils.createWebResponse("Student have been updated successfully.",
        HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteStudent(@RequestParam("id") String studentId,
      @RequestParam("updatedBy") String updatedBy)
      throws Exception {
    studentService.deleteStudent(studentId, updatedBy);
    return WebResponseUtils.createWebResponse("Delete Success", HttpStatus.OK);
  }

  @PostMapping("register/{student}/{course}")
  public ResponseEntity<?> registerCourse(@PathVariable("student") String student,
      @PathVariable("course") String course) throws Exception {
    studentService.RegisterCourse(student, course);
    return WebResponseUtils.createWebResponse("Success Register Course", HttpStatus.CREATED);
  }

  @GetMapping("report/{id}")
  public ResponseEntity<?> getStudentReportJasper(@PathVariable("id") String studentId)
      throws Exception {
    Student student = studentService.getStudentById(studentId);
    Map<String, Object> mapStudent = new HashMap<>();
    mapStudent.put("modelStudentFName", student.getUser().getFirstName());
    mapStudent.put("modelStudentLName", student.getUser().getLastName());
    mapStudent.put("modelStudentEmail", student.getUser().getEmail());
    mapStudent.put("modelStudentGender", student.getGender().toString());
    mapStudent.put("modelStudentPhone", student.getPhone());
    byte[] out;
    try {
      List<StudentReportDTO> listData = studentService.getStudentExamReport(studentId);
      out = JasperUtil.responseToByteArray(listData, "StudentReports", mapStudent);
    } catch (Exception e) {
      e.printStackTrace();
      return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_PDF);
    return ResponseEntity.ok()
        .headers(header)
        .body(new ByteArrayResource(out));
  }

  @GetMapping("report")
  public ResponseEntity<?> getStudentReport(@RequestParam("studentId") String studentId)
      throws Exception {
    return WebResponseUtils.createWebResponse(studentService.getStudentExamReport(studentId),
        HttpStatus.OK);
  }

  @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<?> getAllStudent() throws Exception {
    return WebResponseUtils.createWebResponse(studentService.getAll(), HttpStatus.OK);
  }


  // REPORT FOR ADMIN
  // @GetMapping(value = "/report/admin")
  // public ResponseEntity<?> getStudentReport() throws Exception {
  // List<RegisteredStudentCountMonthlyResponseDto> listDto =
  // studentService.countTotalRegisteredStudent();
  // HttpHeaders headers = new HttpHeaders();
  // headers.setContentType(MediaType.APPLICATION_PDF);
  // DashboardStudentResponseDto dashboardDto = studentService.getStudentDataForAdmin();
  // Map<String, Object> params = new HashMap<>();
  // params.put("verified", dashboardDto.getVerified());
  // params.put("active", dashboardDto.getActive());
  // params.put("inActive", dashboardDto.getInactive());
  // params.put("male", dashboardDto.getMale());
  // params.put("female", dashboardDto.getFemale());
  // params.put("total", dashboardDto.getTotal());
  // byte[] out = JasperUtil.responseToByteArray(listDto, "StudentDataReport", params);
  // return ResponseEntity.ok().headers(headers).body(out);
  // }

}
