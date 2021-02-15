package com.lawencon.elearning.service;

import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.dto.module.ModuleListReponseDTO;
import com.lawencon.elearning.dto.module.ModuleRequestDTO;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.dto.module.UpdateModuleDTO;
import com.lawencon.elearning.model.Module;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author WILLIAM
 *
 */
public interface ModuleService {

  Module getModuleById(String id) throws Exception;

  Module getModuleByIdCustom(String id) throws Exception;

  List<ModuleResponseDTO> getModuleListByIdCourse(String idCourse, String idStudent)
      throws Exception;

  void insertModule(List<ModuleRequestDTO> data) throws Exception;

  void updateModule(UpdateModuleDTO data) throws Exception;

  void deleteModule(UpdateIsActiveRequestDTO data) throws Exception;

  void updateIsActiveFalse(String id, String userId) throws Exception;

  void updateIsActiveTrue(UpdateIsActiveRequestDTO data) throws Exception;

  void saveLesson(List<MultipartFile> multiPartFiles, String idUser, String body) throws Exception;

  List<FileResponseDto> getLessonFile(String idModule) throws Exception;

  List<ModuleListReponseDTO> getModuleList(String idCourse) throws Exception;

}
