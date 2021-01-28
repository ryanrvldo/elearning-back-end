package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
public interface SubjectCategoryDao {

  List<SubjectCategory> getAllSubject() throws Exception;

  SubjectCategory getById(String id) throws Exception;

  void updateSubject(SubjectCategory data, Callback before) throws Exception;

  void addSubject(SubjectCategory data, Callback before) throws Exception;

  void deleteSubject(String id) throws Exception;

  void updateIsActive(String id, String idUser) throws Exception;

}
