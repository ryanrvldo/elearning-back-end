package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CourseTypeDao;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class CourseTypeDaoImpl extends CustomBaseDao<CourseType> implements CourseTypeDao {

  @Override
  public List<CourseType> getListCourseType() throws Exception {
    return getAll();
  }

  @Override
  public void insertCourseType(CourseType courseType, Callback before) throws Exception {
    save(courseType, before, null, true, true);
  }

  @Override
  public void updateCourseType(CourseType courseType, Callback before) throws Exception {
    save(courseType, before, null, true, true);
  }

  @Override
  public void deleteCourseType(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public void updateIsActive(String id, String updatedBy, boolean status) throws Exception {
    String sql = buildQueryOf("UPDATE tb_m_course_types SET is_active = ?1 ",
        ", updated_at = now(), updated_by = ?2 , version = (version + 1) WHERE id = ?3").toString();

    createNativeQuery(sql).setParameter(1, status).setParameter(2, updatedBy).setParameter(3, id)
        .executeUpdate();
  }

  @Override
  public CourseType getTypeById(String id) throws Exception {
    return getById(id);
  }
}
