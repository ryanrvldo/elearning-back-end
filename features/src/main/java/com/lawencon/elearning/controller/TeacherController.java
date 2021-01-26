package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.service.TeacherService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 *  @author Dzaky Fadhilla Guci
 */

@RestController
@RequestMapping("/teacher")
public class TeacherController {

  @Autowired
  private TeacherService teacherService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getTeacherProfile(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createSuccessResponse(teacherService.findTeacherByIdCustom(id),
        HttpStatus.OK);
  }
}
