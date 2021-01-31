package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.subject.CreateSubjectCategoryRequestDTO;
import com.lawencon.elearning.dto.subject.UpdateSubjectCategoryRequestDTO;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
public interface SubjectCategoryService {

  List<SubjectCategory> getAllSubject() throws Exception;

  void updateSubject(UpdateSubjectCategoryRequestDTO data, Callback before) throws Exception;

  void addSubject(CreateSubjectCategoryRequestDTO data, Callback before) throws Exception;

  void deleteSubject(DeleteMasterRequestDTO data) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  SubjectCategory getById(String id) throws Exception;

}
