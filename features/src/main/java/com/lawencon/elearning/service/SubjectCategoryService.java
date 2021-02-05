package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.subject.CreateSubjectCategoryRequestDTO;
import com.lawencon.elearning.dto.subject.SubjectCategoryResponseDTO;
import com.lawencon.elearning.dto.subject.UpdateSubjectCategoryRequestDTO;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
public interface SubjectCategoryService {

  List<SubjectCategoryResponseDTO> getAllSubject() throws Exception;

  void updateSubject(UpdateSubjectCategoryRequestDTO data, Callback before) throws Exception;

  void addSubject(CreateSubjectCategoryRequestDTO data, Callback before) throws Exception;

  void deleteSubject(String id) throws Exception;

  void setActiveFalse(DeleteMasterRequestDTO data) throws Exception;

  void setActiveTrue(DeleteMasterRequestDTO data) throws Exception;

  SubjectCategoryResponseDTO getById(String id) throws Exception;

}
