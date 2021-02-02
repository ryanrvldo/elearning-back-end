package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.course.DetailCourseResponseDTO;
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

  List<ModuleResponseDTO> getDetailCourse(String idCourse) throws Exception;

  void insertModule(List<ModulRequestDTO> data) throws Exception;

  void updateModule(UpdateModuleDTO data) throws Exception;

  void deleteModule(DeleteMasterRequestDTO data) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  DetailCourseResponseDTO getDetailCourses(String id) throws Exception;
}
