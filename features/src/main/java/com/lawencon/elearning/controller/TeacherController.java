package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.lawencon.elearning.dto.teacher.TeacherRequestDTO;
import com.lawencon.elearning.dto.teacher.UpdateTeacherRequestDTO;
import com.lawencon.elearning.service.TeacherService;
import com.lawencon.elearning.util.WebResponseUtils;

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

  @GetMapping("reports/{id}")
  public ResponseEntity<?> getReport(@PathVariable("id") String moduleId) throws Exception {
    return WebResponseUtils.createWebResponse(teacherService.getTeacherDetailCourseReport(moduleId),
        HttpStatus.OK);
  }

  @PutMapping("verify/student")
  public ResponseEntity<?> verifyStudentRegisterCourse(
      @RequestParam("studentCourseId") String studentCourseId,
      @RequestParam("teacherId") String teacherId, @RequestParam("email") String email)
      throws Exception {
    teacherService.verifyRegisterStudentCourse(studentCourseId, teacherId, email);
    return WebResponseUtils.createWebResponse("Register Success", HttpStatus.OK);
  }
}
