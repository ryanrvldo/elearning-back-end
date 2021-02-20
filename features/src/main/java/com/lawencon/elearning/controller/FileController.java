package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.service.FileService;

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
        .headers(
            httpHeaders -> httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue()))
        .contentType(MediaType.parseMediaType(file.getContentType()))
        .body(new ByteArrayResource(file.getData()));
  }

}
