package com.lawencon.elearning.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.dto.module.ModulRequestDTO;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.dto.module.UpdateModuleDTO;
import com.lawencon.elearning.model.Module;

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

  void insertModule(List<ModulRequestDTO> data) throws Exception;

  void updateModule(UpdateModuleDTO data) throws Exception;

  void deleteModule(DeleteMasterRequestDTO data) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  // DetailCourseResponseDTO getDetailCourses(String idCourse, String idStudent) throws Exception;

  void saveLesson(List<MultipartFile> multiPartFiles, String content, String body) throws Exception;

  List<FileResponseDto> getLessonFile(String idModule) throws Exception;

}
