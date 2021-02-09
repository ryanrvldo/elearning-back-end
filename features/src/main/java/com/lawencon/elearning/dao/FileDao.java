package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.File;

/**
 * @author Rian Rivaldo
 */
public interface FileDao {

  List<File> getAllFile() throws Exception;

  void create(File file) throws Exception;

  File findById(String id) throws Exception;

  void updateFile(File file) throws Exception;

}
