package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.CourseTypeDao;
import com.lawencon.elearning.model.CourseType;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class CourseTypeDaoImpl extends BaseDaoImpl<CourseType>
    implements CourseTypeDao, BaseCustomDao {

  @Override
  public List<CourseType> getListCourseType() throws Exception {
    return getAll();
  }

  @Override
  public void insertCourseType(CourseType courseType) throws Exception {
    save(courseType, null, null, true, true);
  }

  @Override
  public void updateCourseType(CourseType courseType) throws Exception {
    save(courseType, null, null, true, true);
  }

  @Override
  public void deleteCourseType(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public void softDelete(String id) throws Exception {
    String sql =
        bBuilder("UPDATE tb_m_course_types SET is_active = FALSE WHERE id =?1 ").toString();
    createNativeQuery(sql).setParameter(1, id).executeUpdate();

  }



}
