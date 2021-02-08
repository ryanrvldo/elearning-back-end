package com.lawencon.elearning.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.course.CourseCreateRequestDTO;
import com.lawencon.elearning.dto.course.CourseDeleteRequestDTO;
import com.lawencon.elearning.dto.course.CourseUpdateRequestDTO;
import com.lawencon.elearning.dto.course.DetailCourseResponseDTO;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.util.WebResponseUtils;
import com.lawencon.util.JasperUtil;

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

  @GetMapping("/{id}")
  public ResponseEntity<?> getDetailCourse(@PathVariable("id") String courseId,
      @RequestParam(name = "studentId", required = false) String studentId) throws Exception {
    return WebResponseUtils.createWebResponse(courseService.getDetailCourse(courseId, studentId),
        HttpStatus.OK);
  }

  @GetMapping("/report/{id}")
  public ResponseEntity<?> getDetailCourseReport(@PathVariable("id") String courseId,
      @RequestParam(name = "studentId", required = false) String studentId) throws Exception {
    DetailCourseResponseDTO detailCourseDTO = courseService.getDetailCourse(courseId, studentId);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    Map<String, Object> params = new HashMap<>();
    params.put("name", detailCourseDTO.getName());
    params.put("description", detailCourseDTO.getDescription());
    params.put("capacity", detailCourseDTO.getCapacity());
    params.put("total", detailCourseDTO.getTotalStudent());
    byte[] out =
        JasperUtil.responseToByteArray(detailCourseDTO.getModules(), "DetailCourseReport", params);
    return ResponseEntity.ok().headers(headers).body(out);
  }

  @PostMapping("register")
  public ResponseEntity<?> registerCourse(@RequestParam("studentId") String studentId,
      @RequestParam("courseId") String courseId) throws Exception {
    courseService.registerCourse(studentId, courseId);
    return WebResponseUtils.createWebResponse("Register Course Success", HttpStatus.OK);
  }

  @GetMapping("{id}/student")
  public ResponseEntity<?> getStudentCourse(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(courseService.getCourseByStudentId(id),
        HttpStatus.OK);
  }

  @GetMapping("admin")
  public ResponseEntity<?> getListCourse() throws Exception {
    return WebResponseUtils.createWebResponse(courseService.getCourseForAdmin(), HttpStatus.OK);
  }

  @GetMapping("/{id}/students")
  public ResponseEntity<?> getListStudents(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(courseService.getStudentByCourseId(id),
        HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> insertCourse(@RequestBody CourseCreateRequestDTO data) throws Exception {
    courseService.insertCourse(data);
    return WebResponseUtils.createWebResponse("Insert data success ", HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<?> updateCourse(@RequestBody CourseUpdateRequestDTO data) throws Exception {
    courseService.updateCourse(data);
    return WebResponseUtils.createWebResponse("Update data success", HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteCourse(@RequestBody CourseDeleteRequestDTO data) throws Exception {
    courseService.deleteCourse(data);
    return WebResponseUtils.createWebResponse("Delete data success", HttpStatus.OK);
  }

}
