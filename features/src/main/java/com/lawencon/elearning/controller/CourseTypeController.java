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
import com.lawencon.elearning.model.CourseType;
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
    try {
      return WebResponseUtils.createWebResponse(courseTypeService.getListCourseType(),
          HttpStatus.OK);
    } catch (Exception e) {
      return WebResponseUtils.createWebResponse("Data not found", HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<?> insertCourseType(@RequestBody CourseType data) throws Exception{
    try {
      courseTypeService.insertCourseType(data);
      return WebResponseUtils.createWebResponse("Insert data success", HttpStatus.OK);
    } catch (Exception e) {
      return WebResponseUtils.createWebResponse("Insert data failed", HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping
  public ResponseEntity<?> updateCourseType(@RequestBody CourseType data) throws Exception {
    try {
      courseTypeService.updateCourseType(data);
      return WebResponseUtils.createWebResponse("Update data success", HttpStatus.OK);
    } catch (Exception e) {
      return WebResponseUtils.createWebResponse("Update data failed", HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteCourseTypeById(@RequestParam("id") String id) throws Exception {
    try {
      courseTypeService.deleteCourseType(id);
      return WebResponseUtils.createWebResponse("Delete data success", HttpStatus.OK);
    } catch (Exception e) {
      return WebResponseUtils.createWebResponse("Delete data failed", HttpStatus.BAD_REQUEST);
    }
  }
}
