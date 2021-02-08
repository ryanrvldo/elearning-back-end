package com.lawencon.elearning.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.teacher.TeacherReportResponseDTO;
import com.lawencon.elearning.dto.teacher.TeacherRequestDTO;
import com.lawencon.elearning.dto.teacher.UpdateTeacherRequestDTO;
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

  @PatchMapping("/false")
  public ResponseEntity<?> setIsActiveFalse(@RequestBody DeleteMasterRequestDTO deleteReq)
      throws Exception {
    teacherService.setIsActiveFalse(deleteReq);
    return WebResponseUtils.createWebResponse("Delete Teacher Profile Success!", HttpStatus.OK);
  }

  @PatchMapping("/true")
  public ResponseEntity<?> setIsActiveTrue(@RequestBody DeleteMasterRequestDTO deleteReq)
      throws Exception {
    teacherService.setIsActiveTrue(deleteReq);
    return WebResponseUtils.createWebResponse("Delete Teacher Profile Success!", HttpStatus.OK);
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
  public ResponseEntity<?> getTeacherDetailCourseReport(@PathVariable("id") String moduleId)
      throws Exception {
    byte[] out;
    try {
      List<TeacherReportResponseDTO> listData =
          teacherService.getTeacherDetailCourseReport(moduleId);
      out = JasperUtil.responseToByteArray(listData, "TeacherReport", null);
    } catch (Exception e) {
      e.printStackTrace();
      return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_PDF);
    return ResponseEntity.ok().headers(header).body(new ByteArrayResource(out));
  }
}
