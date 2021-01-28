package com.lawencon.elearning.controller;

import com.lawencon.elearning.model.File;
import com.lawencon.elearning.service.FileService;
import com.lawencon.elearning.util.WebResponseUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Rian Rivaldo
 */
@RestController
public class FileController {

  @Autowired
  private FileService fileService;

  @PostMapping(value = "/file")
  public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file, @RequestPart("content") String content)
      throws Exception {

    return WebResponseUtils.createWebResponse(fileService.createFile(file, content), HttpStatus.CREATED);
  }

  @PostMapping(value = "/files")
  public ResponseEntity<?> uploadMultipleFile(@RequestPart("files") List<MultipartFile> files,
      @RequestPart("content") String content)
      throws Exception {
    return WebResponseUtils.createWebResponse(fileService.createMultipleFile(files, content), HttpStatus.CREATED);
  }

  @GetMapping(value = {"/file/{id}"})
  public ResponseEntity<?> downloadFileById(@PathVariable("id") String id) throws Exception {
    File file = fileService.getFileById(id);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(file.getContentType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
        .body(new ByteArrayResource(file.getData()));
  }

  @PatchMapping(value = {"file/{id}"})
  public ResponseEntity<?> updateFile(@RequestPart Map<String, Object> requestPart)
      throws Exception {
    fileService.updateFile(requestPart);
    return WebResponseUtils.createWebResponse("File has been updated successfully.", HttpStatus.OK);
  }

}
