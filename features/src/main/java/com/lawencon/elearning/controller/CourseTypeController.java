package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.course.type.CourseTypeCreateRequestDTO;
import com.lawencon.elearning.dto.course.type.CourseTypeUpdateRequestDTO;
import com.lawencon.elearning.service.CourseTypeService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * @author : Galih Dika Permana
 */
@RestController
@RequestMapping("course/type")
public class CourseTypeController {

  @Autowired
  private CourseTypeService courseTypeService;

  @GetMapping
  public ResponseEntity<?> getListCourseType() throws Exception {
    return WebResponseUtils.createWebResponse(courseTypeService.getListCourseType(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> insertCourseType(@RequestBody CourseTypeCreateRequestDTO data)
      throws Exception {
    courseTypeService.insertCourseType(data);
    return WebResponseUtils.createWebResponse("Insert data course type success",
        HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<?> updateCourseType(@RequestBody CourseTypeUpdateRequestDTO data)
      throws Exception {
    courseTypeService.updateCourseType(data);
    return WebResponseUtils.createWebResponse("Update data course type success", HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteCourseTypeById(@RequestParam("id") String id) throws Exception {
    courseTypeService.deleteCourseType(id);
    return WebResponseUtils.createWebResponse("Delete data course type success", HttpStatus.OK);
  }
}
