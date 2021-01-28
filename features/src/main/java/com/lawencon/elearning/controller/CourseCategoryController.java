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
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.service.CourseCategoryService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * @author : Galih Dika Permana
 */
@RestController
@RequestMapping("course/category")
public class CourseCategoryController {
  @Autowired
  private CourseCategoryService courseCategoryService;

  @GetMapping
  public ResponseEntity<?> getListCourseCategories() throws Exception {
    try {
      return WebResponseUtils.createWebResponse(courseCategoryService.getListCourseCategory(),
          HttpStatus.OK);
    } catch (Exception e) {
      return WebResponseUtils.createWebResponse("Data Not Found", HttpStatus.BAD_REQUEST);
    }

  }

  @PostMapping
  public ResponseEntity<?> insertCourseCategory(@RequestBody CourseCategory data) throws Exception {
    try {
      courseCategoryService.insertCourseCategory(data);
      return WebResponseUtils.createWebResponse("Insert data success", HttpStatus.OK);
    } catch (Exception e) {
      return WebResponseUtils.createWebResponse("Insert data failed", HttpStatus.BAD_REQUEST);
    }

  }

  @PutMapping
  public ResponseEntity<?> updateCourseCategory(@RequestBody CourseCategory data) throws Exception {
    try {
      courseCategoryService.updateCourseCategory(data);
      return WebResponseUtils.createWebResponse("Update data success", HttpStatus.OK);
    } catch (Exception e) {
      return WebResponseUtils.createWebResponse("Update data failed", HttpStatus.BAD_REQUEST);
    }

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCourseCategoryById(@RequestParam("id") String id)
      throws Exception {
    try {
      courseCategoryService.deleteCourseCategory(id);
      return WebResponseUtils.createWebResponse("Delete data success", HttpStatus.OK);
    } catch (Exception e) {
      return WebResponseUtils.createWebResponse("Data not found", HttpStatus.BAD_REQUEST);
    }

  }
}
