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
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.course.category.CourseCategoryCreateRequestDTO;
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

  @PatchMapping("/false")
  public ResponseEntity<?> setIsActiveFalse(@RequestBody DeleteMasterRequestDTO data)
      throws Exception {
    courseCategoryService.setIsActiveFalse(data);
    return WebResponseUtils.createWebResponse("Update is active to false success", HttpStatus.OK);
  }

  @PatchMapping("/true")
  public ResponseEntity<?> setIsActiveTrue(@RequestBody DeleteMasterRequestDTO data)
      throws Exception {
    courseCategoryService.setIsActiveTrue(data);
    return WebResponseUtils.createWebResponse("Update is active to true", HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<?> deleteCourseCategoryById(@PathVariable("id") String id)
      throws Exception {
    courseCategoryService.deleteCourseCategory(id);
    return WebResponseUtils.createWebResponse("Delete data success", HttpStatus.OK);
  }

}
