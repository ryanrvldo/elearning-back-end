package com.lawencon.elearning.controller;

import com.lawencon.elearning.dto.student.RegisterStudentDTO;
import com.lawencon.elearning.service.GuestService;
import com.lawencon.elearning.util.WebResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rian Rivaldo
 */
@RestController
@RequestMapping(value = "/guest", produces = MediaType.APPLICATION_JSON_VALUE)
public class GuestController {

  @Autowired
  private GuestService service;

  @GetMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAllCourse() throws Exception {
    return WebResponseUtils.createWebResponse(service.getCourses(), HttpStatus.OK);
  }

  @GetMapping("/course")
  public ResponseEntity<?> getModuleListByIdCourse(@RequestParam("id") String id)
      throws Exception {
    return WebResponseUtils.createWebResponse(service.getCourseDetail(id),
        HttpStatus.OK);
  }

  @GetMapping(value = "/teachers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAll() throws Exception {
    return WebResponseUtils.createWebResponse(service.getTeachers(), HttpStatus.OK);
  }

  @PostMapping(value = "/student/register")
  public ResponseEntity<?> registerStudent(@RequestBody RegisterStudentDTO body) throws Exception {
    service.registerStudent(body);
    return WebResponseUtils.createWebResponse("Register Success", HttpStatus.OK);
  }

  @PatchMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestParam("email") String email) throws Exception {
    service.forgotPassword(email);
    return WebResponseUtils.createWebResponse("Reset Password Success", HttpStatus.OK);
  }

}
