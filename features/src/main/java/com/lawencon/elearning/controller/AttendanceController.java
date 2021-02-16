package com.lawencon.elearning.controller;

import com.lawencon.elearning.dto.AttendanceRequestDTO;
import com.lawencon.elearning.service.AttendanceService;
import com.lawencon.elearning.util.WebResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Galih Dika Permana
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

  @Autowired
  private AttendanceService attendanceService;

  @GetMapping("/status")
  public ResponseEntity<?> checkAttendanceStatus(@RequestParam("idModule") String idModule,
      @RequestParam("idStudent") String idStudent) throws Exception {
    return WebResponseUtils.createWebResponse(
        attendanceService.checkAttendanceStatus(idModule, idStudent), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<?> getListAttendance(@RequestParam("idCourse") String idCourse,
      @RequestParam("idModule") String idModule) throws Exception {
    return WebResponseUtils
        .createWebResponse(attendanceService.getAttendanceList(idCourse, idModule), HttpStatus.OK);
  }

  @PostMapping("/student")
  public ResponseEntity<?> createAttendance(@RequestBody AttendanceRequestDTO body)
      throws Exception {
    attendanceService.createAttendance(body);
    return WebResponseUtils.createWebResponse("Create attendance Success", HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<?> verifyAttendance(@RequestParam("id") String id,
      @RequestParam("userId") String userId) throws Exception {
    attendanceService.verifyAttendance(id, userId);
    return WebResponseUtils.createWebResponse("Verify data success", HttpStatus.OK);
  }

}
