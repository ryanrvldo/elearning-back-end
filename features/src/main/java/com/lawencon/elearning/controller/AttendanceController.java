package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.service.AttendanceService;

/**
 * @author : Galih Dika Permana
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
  @Autowired
  private AttendanceService attService;

  public ResponseEntity<?> createAttendance(@RequestBody String body) throws Exception {
    Attendance data = new ObjectMapper().readValue(body, Attendance.class);
    attService.createAttendance(data);
    return new ResponseEntity<>("Berhasil Menambahkan Data", HttpStatus.OK);
  }

}
