package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.service.AttendanceService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * @author : Galih Dika Permana
 */
@RestController
@RequestMapping("/attendance/student")
public class AttendanceController {
  @Autowired
  private AttendanceService attService;

  @PostMapping
  public ResponseEntity<?> createAttendance(@RequestBody Attendance body) throws Exception {
    attService.createAttendance(body);
    return WebResponseUtils.createWebResponse("Create attendance Success", HttpStatus.OK);
  }

}
