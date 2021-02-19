package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.SubjectCategoryDao;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.subject.CreateSubjectCategoryRequestDTO;
import com.lawencon.elearning.dto.subject.SubjectCategoryResponseDTO;
import com.lawencon.elearning.dto.subject.UpdateSubjectCategoryRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.SubjectCategoryService;
import com.lawencon.elearning.service.UserService;
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

  @Autowired
  private UserService userService;

  @Override
  public List<SubjectCategoryResponseDTO> getAllSubject() throws Exception {
    List<SubjectCategory> listResult =  subjectCategoryDao.getAllSubject();
    if (listResult.isEmpty()) {
      return Collections.emptyList();
    }

    List<SubjectCategoryResponseDTO> subjectResponses = new ArrayList<>();
    for (SubjectCategory subject : listResult) {
      SubjectCategoryResponseDTO subjectResponse = new SubjectCategoryResponseDTO();
      subjectResponse.setId(subject.getId());
      subjectResponse.setCode(subject.getCode());
      subjectResponse.setName(subject.getSubjectName());
      subjectResponse.setDescription(subject.getDescription());
      subjectResponse.setActive(subject.getIsActive());
      subjectResponses.add(subjectResponse);
    }

    subjectResponses.sort(Comparator.comparing(val -> val.getCode()));
    return subjectResponses;
  }

  @Override
  public void updateSubject(UpdateSubjectCategoryRequestDTO data, Callback before)
      throws Exception {
    validationUtil.validate(data);
    User userDb = userService.getById(data.getUpdatedBy());
    if (!userDb.getRole().getCode().equals(Roles.ADMIN.getCode())) {
      throw new IllegalAccessException("You are unauthorized");
    }

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
    User userDb = userService.getById(data.getCreatedBy());
    if (!userDb.getRole().getCode().equals(Roles.ADMIN.getCode())) {
      throw new IllegalAccessException("You are unauthorized");
    }

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
  public void updateIsActive(UpdateIsActiveRequestDTO data) throws Exception {
    validationUtil.validate(data);
    try {
      begin();
      subjectCategoryDao.updateIsActive(data.getId(), data.getUpdatedBy(), data.getStatus());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

  @Override
  public SubjectCategoryResponseDTO getById(String id) throws Exception {
    validationUtil.validateUUID(id);
    SubjectCategory subject = Optional.ofNullable(subjectCategoryDao.getById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));

    SubjectCategoryResponseDTO subjectResponse = new SubjectCategoryResponseDTO();
    subjectResponse.setId(subject.getId());
    subjectResponse.setCode(subject.getCode());
    subjectResponse.setName(subject.getSubjectName());
    subjectResponse.setDescription(subject.getDescription());
    return subjectResponse;

  }

}
