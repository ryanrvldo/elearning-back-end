package com.lawencon.elearning.controller;

import com.lawencon.elearning.dto.AttendanceResponseDTO;
import com.lawencon.elearning.dto.course.DetailCourseResponseDTO;
import com.lawencon.elearning.dto.student.StudentReportDTO;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.dto.teacher.TeacherReportResponseDTO;
import com.lawencon.elearning.error.InternalServerErrorException;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.service.ReportService;
import com.lawencon.elearning.util.WebResponseUtils;
import com.lawencon.util.JasperUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rian Rivaldo
 */
@RestController
@RequestMapping(value = "/report", produces = MediaType.APPLICATION_PDF_VALUE)
public class ReportController {

  @Autowired
  private ReportService service;

  /**
   * @author William
   */
  @GetMapping("/course")
  public ResponseEntity<?> getDetailCourseReport(@RequestParam("id") String courseId,
      @RequestParam(name = "studentId", required = false) String studentId) throws Exception {
    DetailCourseResponseDTO detailCourseDTO = service.getDetailCourseReport(courseId, studentId);

    Map<String, Object> params = new HashMap<>();
    params.put("name", detailCourseDTO.getName());
    params.put("description", detailCourseDTO.getDescription());
    params.put("capacity", detailCourseDTO.getCapacity());
    params.put("total", detailCourseDTO.getTotalStudent());
    byte[] out =
        JasperUtil.responseToByteArray(detailCourseDTO.getModules(), "DetailCourseReport", params);
    return ResponseEntity.ok()
        .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_PDF)).body(out);
  }

  /**
   * @author Galih Dika Permana
   */
  @GetMapping("/course/attendance")
  public ResponseEntity<?> getCourseAttendanceReport(@RequestParam("courseId") String courseId)
      throws Exception {
    Course course = service.getCourseById(courseId);
    Teacher teacher = service.getTeacherById(course.getTeacher().getId());
    Map<String, Object> mapTeacher = new HashMap<>();
    mapTeacher.put("teacherFName", teacher.getUser().getFirstName());
    mapTeacher.put("teacherLName", teacher.getUser().getLastName());
    mapTeacher.put("teacherEmail", teacher.getUser().getEmail());
    mapTeacher.put("teacherGender", teacher.getGender().toString());
    mapTeacher.put("teacherPhone", teacher.getPhone());
    byte[] out;
    try {
      List<CourseAttendanceReportByTeacher> listData = service.getCourseAttendanceReport(courseId);
      out = JasperUtil.responseToByteArray(listData, "CourseAttendanceReport", mapTeacher);
    } catch (Exception e) {
      e.printStackTrace();
      return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return ResponseEntity.ok()
        .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_PDF))
        .body(new ByteArrayResource(Objects.requireNonNull(out, "Byte is empty")));
  }

  /**
   * @author Galih Dika Permana
   */
  @GetMapping("/teacher")
  public ResponseEntity<?> getTeacherModuleReport(@RequestParam("moduleId") String moduleId,
      @RequestParam("id") String teacherId)
      throws Exception {
    Teacher teacher = service.findTeacherById(teacherId);
    Map<String, Object> mapTeacher = new HashMap<>();
    mapTeacher.put("teacherFName", teacher.getUser().getFirstName());
    mapTeacher.put("teacherLName", teacher.getUser().getLastName());
    mapTeacher.put("teacherEmail", teacher.getUser().getEmail());
    mapTeacher.put("teacherGender", teacher.getGender().toString());
    mapTeacher.put("teacherPhone", teacher.getPhone());
    byte[] out;
    try {
      List<TeacherReportResponseDTO> listData = service.getTeacherDetailCourseReport(moduleId);
      out = JasperUtil.responseToByteArray(listData, "TeacherReport", mapTeacher);
    } catch (Exception e) {
      e.printStackTrace();
      return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_PDF);
    return ResponseEntity.ok().headers(header).body(new ByteArrayResource(out));
  }

  /**
   * @author William
   */
  @GetMapping("/student")
  public ResponseEntity<?> getStudentReportJasper(@RequestParam("id") String studentId)
      throws Exception {
    Student student = service.getStudentById(studentId);
    Map<String, Object> mapStudent = new HashMap<>();
    mapStudent.put("modelStudentFName", student.getUser().getFirstName());
    mapStudent.put("modelStudentLName", student.getUser().getLastName());
    mapStudent.put("modelStudentEmail", student.getUser().getEmail());
    mapStudent.put("modelStudentGender", student.getGender().toString());
    mapStudent.put("modelStudentPhone", student.getPhone());
    byte[] out;
    try {
      List<StudentReportDTO> listData = service.getStudentExamReport(studentId);
      out = JasperUtil.responseToByteArray(listData, "StudentReports", mapStudent);
    } catch (Exception e) {
      throw new InternalServerErrorException(e);
    }

    return ResponseEntity.ok()
        .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_PDF))
        .body(new ByteArrayResource(Objects.requireNonNull(out, () -> "Byte is empty")));
  }

  /**
   * @author William
   */
  @GetMapping("/attendance")
  public ResponseEntity<?> getAttendanceReport(@RequestParam("idCourse") String idCourse,
      @RequestParam("idModule") String idModule) throws Exception {
    List<AttendanceResponseDTO> listAttendance = service.getAttendanceReport(idCourse, idModule);
    Module module = service.getModuleForAttendanceReport(idModule);
    Map<String, Object> params = new HashMap<>();
    params.put("title", module.getTitle());
    params.put("code", module.getCode());
    params.put("date", module.getSchedule().getDate());
    params.put("startTime", module.getSchedule().getStartTime());
    params.put("endTime", module.getSchedule().getEndTime());

    byte[] out = JasperUtil.responseToByteArray(listAttendance, "AttendanceReport", params);
    return ResponseEntity.ok()
        .headers(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_PDF))
        .body(Objects.requireNonNull(out, () -> "Byte is empty"));
  }

}
