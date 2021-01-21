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

  List<SubjectCategory> getAllSubject();

  void updateSubject(SubjectCategory data, Callback before) throws Exception;

  void addSubject(SubjectCategory data, Callback before) throws Exception;

}
