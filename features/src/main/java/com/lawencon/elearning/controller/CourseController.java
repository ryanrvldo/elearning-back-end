package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.service.CourseService;

/**
 * @author : Galih Dika Permana
 */
@RestController
@RequestMapping("/course")
public class CourseController {
  @Autowired
  private CourseService courseService;


}
