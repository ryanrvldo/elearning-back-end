package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.FileDao;
import com.lawencon.elearning.dto.file.FileCreateRequestDto;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.dto.file.FileUpdateRequestDto;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.service.FileService;
import com.lawencon.elearning.util.TransactionNumberUtils;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author Rian Rivaldo
 */
@Service
public class FileServiceImpl extends BaseServiceImpl implements FileService {

  @Autowired
  private FileDao fileDao;

  @Autowired
  private ValidationUtil validationUtil;

  @Override
  public FileResponseDto createFile(MultipartFile file, String content) throws Exception {
    return uploadFile(file, content);
  }

  @Override
  public List<FileResponseDto> createMultipleFile(List<MultipartFile> files, String content)
      throws Exception {
    if (files.isEmpty()) {
      throw new IllegalRequestException("There is no file have been inputted yet.");
    }
    List<FileResponseDto> responseList = new ArrayList<>();
    for (MultipartFile file : files) {
      FileResponseDto response = uploadFile(file, content);
      responseList.add(response);
    }
    return responseList;
  }

  @Override
  public File getFileById(String id) throws Exception {
    validationUtil.validateUUID(id);
    try {
      begin();
      File file = fileDao.findById(id);
      commit();
      return file;
    } catch (NoResultException e) {
      clear();
      throw new DataIsNotExistsException("file id", id);
    }
  }

  @Override
  public void updateFile(MultipartFile file, String content) throws Exception {
    if (file == null) {
      throw new IllegalRequestException("File is not inputted!");
    }
    ObjectMapper objectMapper = new ObjectMapper();
    FileUpdateRequestDto requestContent;
    try {
      requestContent = objectMapper.readValue(content, FileUpdateRequestDto.class);
    } catch (JsonProcessingException e) {
      throw new IllegalRequestException("Invalid file content.");
    }

    File prevFile = getFileById(requestContent.getId());
    begin();
    File newFile = new File();
    newFile.setId(prevFile.getId());
    newFile.setCreatedAt(prevFile.getCreatedAt());
    newFile.setCreatedBy(prevFile.getCreatedBy());
    newFile.setUpdatedAt(LocalDateTime.now());
    newFile.setUpdatedBy(requestContent.getUserId());
    newFile.setVersion(prevFile.getVersion());
    newFile.setTrxNumber(prevFile.getTrxNumber());
    newFile.setTrxDate(prevFile.getTrxDate());
    newFile.setType(prevFile.getType());
    newFile.setName(file.getOriginalFilename());
    newFile.setContentType(file.getContentType());
    newFile.setData(file.getBytes());
    newFile.setSize(file.getSize());
    fileDao.updateFile(newFile);
    commit();
  }

  private FileResponseDto uploadFile(MultipartFile multipartFile, String content)
      throws Exception {
    if (multipartFile.isEmpty()) {
      throw new IllegalRequestException("There is no file have been inputted yet.");
    }
    String multipartFileName = multipartFile.getOriginalFilename();
    if (multipartFileName == null || multipartFileName.trim().isEmpty()) {
      throw new IllegalRequestException("file name", multipartFileName);
    }

    ObjectMapper objectMapper = new ObjectMapper();
    FileCreateRequestDto fileRequestDto;
    try {
      fileRequestDto = objectMapper.readValue(content, FileCreateRequestDto.class);
    } catch (JsonProcessingException jsonException) {
      throw new IllegalRequestException("Invalid file content.");
    }

    FileType fileType;
    try {
      fileType = FileType.valueOf(fileRequestDto.getType().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalRequestException("Invalid file type.");
    }

    File file = new File();
    file.setName(multipartFileName);
    file.setContentType(multipartFile.getContentType());
    file.setData(multipartFile.getBytes());
    file.setSize(multipartFile.getSize());
    file.setType(fileType);
    file.setTrxNumber(TransactionNumberUtils.generateFileTrxNumber());
    file.setTrxDate(LocalDate.now());
    file.setCreatedAt(LocalDateTime.now());
    file.setCreatedBy(fileRequestDto.getUserId());
    fileDao.create(file);

    return new FileResponseDto(
        file.getId(),
        file.getName(),
        file.getType(),
        file.getSize(),
        file.getContentType(),
        file.getVersion());
  }

}
