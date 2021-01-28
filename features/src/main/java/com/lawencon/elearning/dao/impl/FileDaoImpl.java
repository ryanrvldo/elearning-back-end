package com.lawencon.elearning.dao.impl;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.FileDao;
import com.lawencon.elearning.model.File;
import com.lawencon.util.Callback;
import org.springframework.stereotype.Repository;

/**
 * @author Rian Rivaldo
 */
@Repository
public class FileDaoImpl extends BaseDaoImpl<File> implements FileDao {

  @Override
  public void create(File file) throws Exception {
    save(file, null, null, true, true);
  }

  @Override
  public File findById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public void updateFile(File file, Callback beforeCallback) throws Exception {
    save(file, beforeCallback, null, true, true);
  }

}
