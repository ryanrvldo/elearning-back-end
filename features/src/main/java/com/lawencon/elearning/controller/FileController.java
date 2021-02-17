package com.lawencon.elearning.controller;

import com.lawencon.elearning.model.File;
import com.lawencon.elearning.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rian Rivaldo
 */
@RestController
public class FileController {

  @Autowired
  private FileService fileService;

  @GetMapping(value = {"/file/{id}"})
  public ResponseEntity<?> downloadFileById(@PathVariable("id") String id) throws Exception {
    File file = fileService.getFileById(id);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(file.getContentType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
        .body(new ByteArrayResource(file.getData()));
  }

}
