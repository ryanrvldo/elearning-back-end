package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.SubjectCategoryDao;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.subject.CreateSubjectCategoryRequestDTO;
import com.lawencon.elearning.dto.subject.SubjectCategoryResponseDTO;
import com.lawencon.elearning.dto.subject.UpdateSubjectCategoryRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
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
  public List<SubjectCategoryResponseDTO> getAllSubject() throws Exception {
    List<SubjectCategory> listResult =  subjectCategoryDao.getAllSubject();
    if (listResult.isEmpty()) {
      throw new DataIsNotExistsException("No Subject Category Yet");
    }

    List<SubjectCategoryResponseDTO> subjectResponses = new ArrayList<SubjectCategoryResponseDTO>();

    for (SubjectCategory subject : listResult) {
      SubjectCategoryResponseDTO subjectResponse = new SubjectCategoryResponseDTO();
      subjectResponse.setId(subject.getId());
      subjectResponse.setCode(subject.getCode());
      subjectResponse.setName(subject.getSubjectName());
      subjectResponse.setDescription(subject.getDescription());
      subjectResponses.add(subjectResponse);
    }
    return subjectResponses;
  }

  @Override
  public void updateSubject(UpdateSubjectCategoryRequestDTO data, Callback before)
      throws Exception {
    validationUtil.validate(data);
    SubjectCategory subject = new SubjectCategory();
    subject.setId(data.getId());
    subject.setCode(data.getCode());
    subject.setDescription(data.getDescription());
    subject.setSubjectName(data.getSubjectName());
    subject.setUpdatedBy(data.getUpdatedBy());
    setupUpdatedValue(subject, () -> Optional.ofNullable(subjectCategoryDao.getById(data.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("id", data.getId())));
    subjectCategoryDao.updateSubject(subject, before);
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
    validationUtil.validateUUID(id);
    try {
      begin();
      subjectCategoryDao.deleteSubject(id);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public void setActiveFalse(DeleteMasterRequestDTO data) throws Exception {
    validationUtil.validate(data);
    try {
      begin();
      subjectCategoryDao.setActiveFalse(data.getId(), data.getUpdatedBy());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public void setActiveTrue(DeleteMasterRequestDTO data) throws Exception {
    validationUtil.validate(data);
    try {
      begin();
      subjectCategoryDao.setActiveTrue(data.getId(), data.getUpdatedBy());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public SubjectCategoryResponseDTO getById(String id) throws Exception {
    validateNullId(id, "id");
    SubjectCategory subject = Optional.ofNullable(subjectCategoryDao.getById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));

    SubjectCategoryResponseDTO subjectResponse = new SubjectCategoryResponseDTO();
    subjectResponse.setId(subject.getId());
    subjectResponse.setCode(subject.getCode());
    subjectResponse.setName(subject.getSubjectName());
    subjectResponse.setDescription(subject.getDescription());
    return subjectResponse;

  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
