package com.lawencon.elearning.dao.impl;

import java.util.List;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.CourseTypeDao;
import com.lawencon.elearning.model.CourseType;

/**
 * @author : Galih Dika Permana
 */

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



}
