package com.lawencon.elearning.service;

import com.lawencon.elearning.model.File;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Rian Rivaldo
 */
public interface FileService {

  void createFile(MultipartFile multipartFile) throws Exception;

  File getFileById(String id) throws Exception;

  void updateFile(File file) throws Exception;

}
