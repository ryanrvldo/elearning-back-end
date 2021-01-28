package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.elearning.service.SubjectCategoryService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * 
 * @author WILLIAM
 *
 */
@RestController
@RequestMapping("subjectcategory")
public class SubjectCategoryController {

  @Autowired
  private SubjectCategoryService subjectCategoryService;

  @GetMapping
  public ResponseEntity<?> getSubjectCategory() throws Exception {
    return WebResponseUtils.createWebResponse(subjectCategoryService.getAllSubject(),
        HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<?> getSubjectCategoryById(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(subjectCategoryService.getById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> insertSubjectCategory(@RequestBody SubjectCategory body)
      throws Exception {
    subjectCategoryService.addSubject(body, null);
    return WebResponseUtils.createWebResponse("Insert Subject Success", HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<?> updateSubjectCategory(@RequestBody SubjectCategory body)
      throws Exception {
    subjectCategoryService.updateSubject(body, null);
    return WebResponseUtils.createWebResponse("Update Subject Success", HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteSubjectCategory(@PathVariable String id) throws Exception {
    subjectCategoryService.deleteSubject(id);
    return WebResponseUtils.createWebResponse("Delete Subject Success", HttpStatus.OK);
  }

}
