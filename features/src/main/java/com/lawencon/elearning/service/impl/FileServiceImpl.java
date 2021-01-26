package com.lawencon.elearning.service.impl;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.FileDao;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.service.FileService;
import com.lawencon.elearning.util.TransactionNumberUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Rian Rivaldo
 */
@Service
public class FileServiceImpl extends BaseServiceImpl implements FileService {

  @Autowired
  private FileDao fileDao;

  @Override
  public void createFile(MultipartFile multipartFile) throws Exception {
    String multipartFileName = multipartFile.getOriginalFilename();
    if (multipartFileName == null || multipartFileName.trim().isEmpty()) {
      throw new IllegalRequestException("file name", multipartFileName);
    }

    File file = new File();
    file.setName(multipartFileName);
    file.setContentType(multipartFile.getContentType());
    file.setData(multipartFile.getBytes());
    file.setTrxNumber(TransactionNumberUtils.generateFileTrxNumber());
    file.setTrxDate(LocalDate.now());
    file.setSize(multipartFile.getSize());
    file.setCreatedAt(LocalDateTime.now());
    fileDao.create(file);
  }

  @Override
  public File getFileById(String id) throws Exception {
    Optional.ofNullable(id)
        .orElseThrow(() -> new IllegalRequestException("id", id));
    return Optional.ofNullable(fileDao.findById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public void updateFile(File newFile) throws Exception {
    File file = getFileById(newFile.getId());
    newFile.setCreatedAt(file.getCreatedAt());
    newFile.setCreatedBy(file.getCreatedBy());
    newFile.setUpdatedAt(LocalDateTime.now());
    newFile.setVersion(file.getVersion());
    newFile.setTrxNumber(file.getTrxNumber());
    newFile.setTrxDate(file.getTrxDate());
    newFile.setUpdatedAt(LocalDateTime.now());
    fileDao.updateFile(file, null);
  }

}
