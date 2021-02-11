package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.model.Module;
import com.lawencon.util.Callback;
/**
 * 
 * @author WILLIAM
 *
 */
public interface ModuleDao {

  List<Module> getListModule() throws Exception;

  Module getModuleById(String id) throws Exception;

  Module getModuleByIdCustom(String id) throws Exception;

  List<Module> getDetailCourse(String idCourse) throws Exception;

  List<ModuleResponseDTO> getDetailCourseStudent(String idCourse, String idStudent)
      throws Exception;

  void insertModule(Module data, Callback before) throws Exception;

  void insertLesson(String idModule, String idFile) throws Exception;

  void updateModule(Module data, Callback before) throws Exception;

  void deleteModule(String id) throws Exception;

  void updateIsActiveFalse(String id, String userId) throws Exception;

  void updateIsActiveTrue(String id, String userId) throws Exception;

  List<FileResponseDto> getLessonByIdModule(String idModule) throws Exception;

  List<Module> getModuleList(String courseId) throws Exception;

}
