package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.CourseCategoryDao;
import com.lawencon.elearning.model.CourseCategory;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class CourseCategoryDaoImpl extends BaseDaoImpl<CourseCategory>
    implements CourseCategoryDao, BaseCustomDao {

  @Override
  public List<CourseCategory> getListCourseCategory() throws Exception {
    return getAll();
  }

  @Override
  public void insertCourseCategory(CourseCategory courseCategory) throws Exception {
    save(courseCategory, null, null, true, true);
  }

  @Override
  public void updateCourseCategory(CourseCategory courseCategory) throws Exception {
    save(courseCategory, null, null, true, true);
  }

  @Override
  public void deleteCourseCategory(String id) throws Exception {
    deleteById(id);
  }



}
