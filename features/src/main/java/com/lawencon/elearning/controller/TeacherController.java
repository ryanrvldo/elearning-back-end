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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.dto.teacher.TeacherReportResponseDTO;
import com.lawencon.elearning.dto.teacher.TeacherRequestDTO;
import com.lawencon.elearning.dto.teacher.UpdateTeacherRequestDTO;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.service.TeacherService;
import com.lawencon.elearning.util.WebResponseUtils;
import com.lawencon.util.JasperUtil;

/**
 * @author Dzaky Fadhilla Guci
 */

@RestController
@RequestMapping("/teacher")
public class TeacherController {

  @Autowired
  private TeacherService teacherService;

  @GetMapping
  public ResponseEntity<?> getAllTeachers() throws Exception {
    return WebResponseUtils.createWebResponse(teacherService.allTeachersForAdmin(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getTeacherProfile(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(teacherService.findTeacherByIdCustom(id),
        HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createTeacherProfile(@RequestBody TeacherRequestDTO body)
      throws Exception {
    teacherService.saveTeacher(body);
    return WebResponseUtils.createWebResponse("Create Teacher Profile Success!",
        HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<?> updateTeacherProfile(@RequestBody UpdateTeacherRequestDTO body)
      throws Exception {
    teacherService.updateTeacher(body);
    return WebResponseUtils.createWebResponse("Update Teacher Profile Success!", HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<?> updateIsActive(@RequestBody UpdateIsActiveRequestDTO deleteReq)
      throws Exception {
    teacherService.updateIsActive(deleteReq);
    return WebResponseUtils.createWebResponse("Update Is Active Teacher Success!", HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<?> deleteTeacherById(@PathVariable("id") String id)
      throws Exception {
    teacherService.deleteTeacherById(id);
    return WebResponseUtils.createWebResponse("Delete Teacher Profile Success!", HttpStatus.OK);
  }

  @GetMapping("/dashboard/{id}")
  public ResponseEntity<?> getTeacherCourse(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(teacherService.getTeacherDashboard(id),
        HttpStatus.OK);
  }

  @GetMapping("report/{id}")
  public ResponseEntity<?> getTeacherDetailCourseReport(@PathVariable("id") String moduleId
  , @RequestParam("id") String teacherId)
      throws Exception {
    Teacher teacher = teacherService.findTeacherById(teacherId);
    Map<String, Object> mapTeacher = new HashMap<>();
    mapTeacher.put("teacherFName", teacher.getUser().getFirstName());
    mapTeacher.put("teacherLName", teacher.getUser().getLastName());
    mapTeacher.put("teacherEmail", teacher.getUser().getEmail());
    mapTeacher.put("teacherGender", teacher.getGender().toString());
    mapTeacher.put("teacherPhone", teacher.getPhone());
    byte[] out;
    try {
      List<TeacherReportResponseDTO> listData =
          teacherService.getTeacherDetailCourseReport(moduleId);
      out = JasperUtil.responseToByteArray(listData, "TeacherReport", mapTeacher);
    } catch (Exception e) {
      e.printStackTrace();
      return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_PDF);
    return ResponseEntity.ok().headers(header).body(new ByteArrayResource(out));
  }

  @GetMapping("attendance/report/{id}")
  public ResponseEntity<?> getCourseAttendanceReport(@PathVariable("id") String courseId)
      throws Exception {
    Course course = teacherService.getCourseById(courseId);
    Teacher teacher = teacherService.findTeacherById(course.getTeacher().getId());
    Map<String, Object> mapTeacher = new HashMap<>();
    mapTeacher.put("teacherFName", teacher.getUser().getFirstName());
    mapTeacher.put("teacherLName", teacher.getUser().getLastName());
    mapTeacher.put("teacherEmail", teacher.getUser().getEmail());
    mapTeacher.put("teacherGender", teacher.getGender().toString());
    mapTeacher.put("teacherPhone", teacher.getPhone());
    byte[] out;
    try {
      List<CourseAttendanceReportByTeacher> listData =
          teacherService.getCourseAttendanceReport(courseId);
      out = JasperUtil.responseToByteArray(listData, "CourseAttendanceReport", mapTeacher);
    } catch (Exception e) {
      e.printStackTrace();
      return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_PDF);
    return ResponseEntity.ok().headers(header).body(new ByteArrayResource(out));
  }

  @GetMapping("attendance/reports/{id}")
  public ResponseEntity<?> getCourseAttendanceReports(@PathVariable("id") String courseId)
      throws Exception {
    return WebResponseUtils.createWebResponse(teacherService.getCourseAttendanceReport(courseId),
        HttpStatus.OK);
  }
}
