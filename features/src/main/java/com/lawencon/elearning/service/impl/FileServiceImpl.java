package com.lawencon.elearning.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.FileDao;
import com.lawencon.elearning.dto.FileRequestDto;
import com.lawencon.elearning.dto.FileResponseDto;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.service.FileService;
import com.lawencon.elearning.util.TransactionNumberUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
  public FileResponseDto createFile(MultipartFile file, String content) throws Exception {
    return uploadFile(file, content);
  }

  @Override
  public List<FileResponseDto> createMultipleFile(List<MultipartFile> files, String content)
      throws Exception {
    List<FileResponseDto> responseList = new ArrayList<>();
    for (MultipartFile file : files) {
      FileResponseDto response = uploadFile(file, content);
      responseList.add(response);
    }
    return responseList;
  }

  @Override
  public File getFileById(String id) throws Exception {
    Optional.ofNullable(id)
        .orElseThrow(() -> new IllegalRequestException("id", id));
    begin();
    File file = Optional.ofNullable(fileDao.findById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
    commit();
    return file;
  }

  @Override
  public void updateFile(Map<String, Object> requestPart) throws Exception {
    String fileId = (String) requestPart.get("id");
    String userId = (String) requestPart.get("userId");
    MultipartFile requestFile = (MultipartFile) requestPart.get("file");

    File prevFile = getFileById(fileId);
    File newFile = new File();
    newFile.setCreatedAt(prevFile.getCreatedAt());
    newFile.setCreatedBy(prevFile.getCreatedBy());
    newFile.setUpdatedAt(LocalDateTime.now());
    newFile.setUpdatedBy(userId);
    newFile.setVersion(prevFile.getVersion());
    newFile.setTrxNumber(prevFile.getTrxNumber());
    newFile.setTrxDate(prevFile.getTrxDate());
    newFile.setType(prevFile.getType());

    newFile.setName(requestFile.getOriginalFilename());
    newFile.setContentType(requestFile.getContentType());
    newFile.setData(requestFile.getBytes());
    newFile.setSize(requestFile.getSize());
    fileDao.updateFile(newFile, null);
  }

  private FileResponseDto uploadFile(MultipartFile multipartFile, String content)
      throws Exception {
    String multipartFileName = multipartFile.getOriginalFilename();
    if (multipartFileName == null || multipartFileName.trim().isEmpty()) {
      throw new IllegalRequestException("file name", multipartFileName);
    }
    ObjectMapper objectMapper = new ObjectMapper();
    FileRequestDto fileRequestDto = objectMapper.readValue(content, FileRequestDto.class);

    FileType fileType = FileType.valueOf(fileRequestDto.getType());
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

    return new FileResponseDto(file.getId(), file.getName(), file.getType(),
        file.getSize(), file.getContentType(), file.getVersion());
  }

}
