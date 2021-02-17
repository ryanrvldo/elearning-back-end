package com.lawencon.elearning.dao.impl;

import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.FileDao;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.FileType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;

/**
 * @author Rian Rivaldo
 */
@Repository
public class FileDaoImpl extends CustomBaseDao<File> implements FileDao {

  @Override
  public void create(File file) throws Exception {
    save(file, null, null, false, false);
  }

  @Override
  public File findById(String id) throws Exception {
    String hql = buildQueryOf(
        "SELECT id, name, data, type, contentType, size, trxDate, trxNumber, createdAt, createdBy, version ",
        "FROM File WHERE id = ?1");
    File file = new File();
    Object[] objArr = createQuery(hql, Object[].class)
        .setParameter(1, id)
        .getSingleResult();
    file.setId((String) objArr[0]);
    file.setName((String) objArr[1]);
    file.setData((byte[]) objArr[2]);
    file.setType((FileType) objArr[3]);
    file.setContentType((String) objArr[4]);
    file.setSize((Long) objArr[5]);
    file.setTrxDate((LocalDate) objArr[6]);
    file.setTrxNumber((String) objArr[7]);
    file.setCreatedAt((LocalDateTime) objArr[8]);
    file.setCreatedBy((String) objArr[9]);
    file.setVersion((Long) objArr[10]);
    return file;
  }

  @Override
  public void updateFile(File file) throws Exception {
    save(file, null, null, false, false);
  }

  @Override
  public void deleteById(String id) throws Exception {
    super.deleteById(id);
  }
}
