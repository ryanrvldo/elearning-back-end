package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * @author : Galih Dika Permana
 */
@RestController
@RequestMapping("/course")
public class CourseController {
  @Autowired
  private CourseService courseService;

  @GetMapping("/available")
  public ResponseEntity<?> getCourseAvailable() throws Exception {
    return WebResponseUtils.createWebResponse(courseService.getCurentAvailableCourse(),
        HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerCourse() throws Exception {
    return WebResponseUtils.createWebResponse(null, HttpStatus.OK);
  }
  
  @GetMapping("/{id}/students")
  public ResponseEntity<?> getStudentCourse(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(courseService.getCurentAvailableCourse(),
        HttpStatus.OK);
  }

}
