package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
public interface SubjectCategoryService {

  List<SubjectCategory> getAllSubject() throws Exception;

  void updateSubject(SubjectCategory data, Callback before) throws Exception;

  void addSubject(SubjectCategory data, Callback before) throws Exception;

  void deleteSubject(String id) throws Exception;

  void updateIsActive(String id, String userId) throws Exception;

  SubjectCategory getById(String id) throws Exception;

}
