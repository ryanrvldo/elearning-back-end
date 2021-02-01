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
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.course.category.CourseCategoryCreateRequestDTO;
import com.lawencon.elearning.dto.course.category.CourseCategoryDeleteRequestDTO;
import com.lawencon.elearning.dto.course.category.CourseCategoryUpdateRequestDTO;
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
    return WebResponseUtils.createWebResponse(courseCategoryService.getListCourseCategory(),
        HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> insertCourseCategory(@RequestBody CourseCategoryCreateRequestDTO data)
      throws Exception {
    courseCategoryService.insertCourseCategory(data);
    return WebResponseUtils.createWebResponse("Insert data success", HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<?> updateCourseCategory(@RequestBody CourseCategoryUpdateRequestDTO data)
      throws Exception {
    courseCategoryService.updateCourseCategory(data);
    return WebResponseUtils.createWebResponse("Update data success", HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteCourseCategoryById(
      @RequestBody CourseCategoryDeleteRequestDTO data)
      throws Exception {
    courseCategoryService.deleteCourseCategory(data);
    return WebResponseUtils.createWebResponse("Delete data success", HttpStatus.OK);

  }
}
