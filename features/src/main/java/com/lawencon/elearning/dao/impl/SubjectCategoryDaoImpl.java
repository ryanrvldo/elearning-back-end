package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.SubjectCategoryDao;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
@Repository
public class SubjectCategoryDaoImpl extends CustomBaseDao<SubjectCategory>
    implements SubjectCategoryDao {

  @Override
  public List<SubjectCategory> getAllSubject() {
    return getAll();
  }

  @Override
  public SubjectCategory getById(String id) {
    return super.getById(id);
  }

  @Override
  public void updateSubject(SubjectCategory data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public void addSubject(SubjectCategory data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public void deleteSubject(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    String sql = "UPDATE tb_m_subject_categories SET is_active = false";
    updateNativeSQL(sql, id, userId);
  }

}
