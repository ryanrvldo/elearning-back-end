package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.SubjectCategoryDao;
import com.lawencon.elearning.dto.subject.CreateSubjectCategoryRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.elearning.service.SubjectCategoryService;
import com.lawencon.elearning.util.ValidationUtil;
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

  @Autowired
  private ValidationUtil validationUtil;

  @Override
  public List<SubjectCategory> getAllSubject() throws Exception {
    List<SubjectCategory> listResult =  subjectCategoryDao.getAllSubject();
    if (listResult.isEmpty()) {
      throw new DataIsNotExistsException("No Subject Category Yet");
    }
    return listResult;
  }

  @Override
  public void updateSubject(SubjectCategory data, Callback before) throws Exception {
    setupUpdatedValue(data, () -> subjectCategoryDao.getById(data.getId()));
    subjectCategoryDao.updateSubject(data, before);
  }

  @Override
  public void addSubject(CreateSubjectCategoryRequestDTO data, Callback before) throws Exception {
    validationUtil.validate(data);
    SubjectCategory subject = new SubjectCategory();
    subject.setCode(data.getCode());
    subject.setDescription(data.getDescription());
    subject.setSubjectName(data.getSubjectName());
    subject.setCreatedBy(data.getCreatedBy());
    subject.setCreatedAt(LocalDateTime.now());
    subjectCategoryDao.addSubject(subject, before);
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
