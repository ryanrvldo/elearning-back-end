package com.lawencon.elearning.service.impl;

import java.util.List;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseCategoryDao;
import com.lawencon.elearning.dao.impl.CourseCategoryDaoImpl;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.service.CourseCategoryService;

/**
 * @author : Galih Dika Permana
 */

public class CourseCategoryServiceImpl extends BaseServiceImpl implements CourseCategoryService {

  private CourseCategoryDao courseDao = new CourseCategoryDaoImpl();
  @Override
  public List<CourseCategory> getListCourseCategory() throws Exception {
    return courseDao.getListCourseCategory();
  }

  @Override
  public void insertCourseCategory(CourseCategory courseCategory) throws Exception {
    courseDao.insertCourseCategory(courseCategory);
  }

  @Override
  public void updateCourseCategory(CourseCategory courseCategory) throws Exception {
    courseDao.updateCourseCategory(courseCategory);
  }

  @Override
  public void deleteCourseCategory(String id) throws Exception {
    courseDao.deleteCourseCategory(id);
  }

  @Override
  public void softDelete(String id) throws Exception {
    try {
      begin();
      courseDao.softDelete(id);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
