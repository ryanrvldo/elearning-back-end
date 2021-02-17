package com.lawencon.elearning.controller;

import com.lawencon.elearning.service.GuestService;
import com.lawencon.elearning.util.WebResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rian Rivaldo
 */
@RestController
@RequestMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
public class GuestController {

  @Autowired
  private GuestService service;

  @GetMapping(value = "/courses")
  public ResponseEntity<?> getAllCourse() throws Exception {
    return WebResponseUtils.createWebResponse(service.getCourses(), HttpStatus.OK);
  }

  @GetMapping(value = "/course/{id}")
  public ResponseEntity<?> getModuleListByIdCourse(@PathVariable("id") String id)
      throws Exception {
    return WebResponseUtils.createWebResponse(service.getCourseDetail(id),
        HttpStatus.OK);
  }

  @GetMapping(value = "/teachers")
  public ResponseEntity<?> getAll() throws Exception {
    return WebResponseUtils.createWebResponse(service.getTeachers(), HttpStatus.OK);
  }

}
