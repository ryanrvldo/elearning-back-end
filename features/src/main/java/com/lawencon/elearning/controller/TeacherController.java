package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.TeacherRequestDTO;
import com.lawencon.elearning.dto.UpdateTeacherRequestDTO;
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
    return WebResponseUtils.createWebResponse(teacherService.findTeacherByIdCustom(id),
        HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createTeacherProfile(@RequestBody TeacherRequestDTO body)
      throws Exception {
    teacherService.saveTeacher(body);
    return WebResponseUtils.createWebResponse("Create Teacher Profile Success!",
        HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<?> updateTeacherProfile(@RequestBody UpdateTeacherRequestDTO body) {
    return WebResponseUtils.createWebResponse("Update Teacher Profile Success!", HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<?> updateIsActiveProfile() throws Exception {
    return WebResponseUtils.createWebResponse("Update is active Profile to false Success!",
        HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteTeacher(@PathVariable("id") String id) throws Exception {
    teacherService.deleteTeacherById(id);
    return WebResponseUtils.createWebResponse("Delete Teacher Profile Success!", HttpStatus.OK);
  }

}
