package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.module.ModulRequestDTO;
import com.lawencon.elearning.model.Module;

/**
 * 
 * @author WILLIAM
 *
 */
public interface ModuleService {

  Module getModuleById(String id) throws Exception;

  Module getModuleByIdCustom(String id) throws Exception;

  List<Module> getDetailCourse(String idCourse) throws Exception;

  void insertModule(List<ModulRequestDTO> data) throws Exception;

  void updateModule(Module data) throws Exception;

  void deleteModule(String id) throws Exception;

}
