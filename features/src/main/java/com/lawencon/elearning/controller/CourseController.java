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
@RequestMapping("course")
public class CourseController {
  @Autowired
  private CourseService courseService;

  @GetMapping("available")
  public ResponseEntity<?> getCourseAvailable() throws Exception {
    return WebResponseUtils.createWebResponse(courseService.getCurrentAvailableCourse(),
        HttpStatus.OK);
  }

  @PostMapping("register/{student}/{course}")
  public ResponseEntity<?> registerCourse(@PathVariable("student") String student,
      @PathVariable("course") String course) throws Exception {
    courseService.registerCourse(student, course);
    return WebResponseUtils.createWebResponse("Register Course Success", HttpStatus.OK);
  }
  
  @GetMapping("{id}/student")
  public ResponseEntity<?> getStudentCourse(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(courseService.getMyCourse(id),
        HttpStatus.OK);
  }

  @GetMapping("admin")
  public ResponseEntity<?> getListCourse() throws Exception {
    return WebResponseUtils.createWebResponse(courseService.getCourseForAdmin(), HttpStatus.OK);
  }

}
