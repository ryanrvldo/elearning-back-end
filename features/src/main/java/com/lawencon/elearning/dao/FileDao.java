package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.File;

/**
 * @author Rian Rivaldo
 */
public interface FileDao {

  void create(File file) throws Exception;

  File findById(String id) throws Exception;

  void updateFile(File file) throws Exception;

  void deleteById(String id) throws Exception;

}
