package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.AttendanceRequestDTO;
import com.lawencon.elearning.service.AttendanceService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * @author : Galih Dika Permana
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
  @Autowired
  private AttendanceService attendanceService;

  @PostMapping("/student")
  public ResponseEntity<?> createAttendance(@RequestBody AttendanceRequestDTO body)
      throws Exception {
    attendanceService.createAttendance(body);
    return WebResponseUtils.createWebResponse("Create attendance Success", HttpStatus.OK);
  }

  @PatchMapping("{id}/{userId}")
  public ResponseEntity<?> verifyAttendance(@PathVariable("id") String id,
      @PathVariable("userId") String userId) throws Exception {
    attendanceService.verifyAttendance(id, userId);
    return WebResponseUtils.createWebResponse("Verify data success", HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<?> getListAttendance(@PathVariable("id") String moduleId) throws Exception {
    return WebResponseUtils.createWebResponse(attendanceService.getAttendanceList(moduleId),
        HttpStatus.OK);
  }

}
