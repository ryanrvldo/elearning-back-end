package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.SubjectCategoryDao;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.elearning.service.SubjectCategoryService;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
@Service
public class SubjectCategoryServiceImpl extends BaseServiceImpl implements SubjectCategoryService {

  @Autowired
  private SubjectCategoryDao subjectCategoryDao;

  @Override
  public List<SubjectCategory> getAllSubject() throws Exception {
    List<SubjectCategory> listResult =  subjectCategoryDao.getAllSubject();
    if(listResult.size() == 0) {
      throw new Exception("Data Is Empty");
    }
    return listResult;
  }

  @Override
  public void updateSubject(SubjectCategory data, Callback before) throws Exception {
    setupUpdatedValue(data, () -> subjectCategoryDao.getById(data.getId()));
    subjectCategoryDao.updateSubject(data, before);
  }

  @Override
  public void addSubject(SubjectCategory data, Callback before) throws Exception {
    data.setCreatedAt(LocalDateTime.now());
    subjectCategoryDao.addSubject(data, before);
  }

  @Override
  public void deleteSubject(String id) throws Exception {
    begin();
    subjectCategoryDao.deleteSubject(id);
    commit();
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    begin();
    subjectCategoryDao.updateIsActive(id, userId);
    commit();
  }

  @Override
  public SubjectCategory getById(String id) throws Exception {
    return Optional.ofNullable(subjectCategoryDao.getById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

}
