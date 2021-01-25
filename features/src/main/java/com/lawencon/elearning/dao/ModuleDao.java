package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.Module;
import com.lawencon.util.Callback;
/**
 * 
 * @author WILLIAM
 *
 */
public interface ModuleDao {

  Module getModuleById(String id) throws Exception;

  List<Module> getDetailCourse(String idCourse) throws Exception;

  void insertModule(Module data, Callback before) throws Exception;

  void updateModule(Module data, Callback before) throws Exception;

  void deleteModule(String id) throws Exception;

  void updateIsActive(String id) throws Exception;

}
