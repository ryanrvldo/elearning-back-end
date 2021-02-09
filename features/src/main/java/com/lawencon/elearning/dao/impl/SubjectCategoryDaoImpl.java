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
  public List<SubjectCategory> getAllSubject() throws Exception {
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
  public void updateIsActive(String id, String updatedBy, boolean status) throws Exception {
    String sql = buildQueryOf("UPDATE tb_m_subject_categories SET is_active = ?1 ",
        ", updated_at = now(), updated_by = ?2 , version = (version + 1) WHERE id = ?3").toString();

    createNativeQuery(sql).setParameter(1, status).setParameter(2, updatedBy).setParameter(3, id)
        .executeUpdate();

  }

}
