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

  Module getById(String id);

  List<Module> getDetailCourse(String id);

  void insertModule(Module data, Callback before) throws Exception;

  void updateModule(Module data, Callback before) throws Exception;

  void deleteModule(String id) throws Exception;

  void softDeleteModule(String id) throws Exception;

}
