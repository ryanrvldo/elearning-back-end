package com.lawencon.elearning.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.SubjectCategoryDao;
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
  SubjectCategoryDao subjectCategoryDao;

  @Override
  public List<SubjectCategory> getAllSubject() {
    return subjectCategoryDao.getAllSubject();
  }

  @Override
  public void updateSubject(SubjectCategory data, Callback before) throws Exception {
    subjectCategoryDao.updateSubject(data, before);
  }

  @Override
  public void addSubject(SubjectCategory data, Callback before) throws Exception {
    subjectCategoryDao.addSubject(data, before);
  }

  @Override
  public void deleteSubject(String id) throws Exception {
    begin();
    subjectCategoryDao.deleteSubject(id);
    commit();
  }

  @Override
  public void updateIsActive(SubjectCategory data) throws Exception {
    data.setIsActive(false);
    subjectCategoryDao.updateIsActive(data, null);
  }

}
