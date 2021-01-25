package com.lawencon.elearning.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseCategoryDao;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.service.CourseCategoryService;

/**
 * @author : Galih Dika Permana
 */

public class CourseCategoryServiceImpl extends BaseServiceImpl implements CourseCategoryService {

  @Autowired
  private CourseCategoryDao courseCategoryDao;

  @Override
  public List<CourseCategory> getListCourseCategory() throws Exception {
    return courseCategoryDao.getListCourseCategory();
  }

  @Override
  public void insertCourseCategory(CourseCategory courseCategory) throws Exception {
    courseCategoryDao.insertCourseCategory(courseCategory, null);
  }

  @Override
  public void updateCourseCategory(CourseCategory courseCategory) throws Exception {
    courseCategoryDao.updateCourseCategory(courseCategory, null);
  }

  @Override
  public void deleteCourseCategory(String id) throws Exception {
    courseCategoryDao.deleteCourseCategory(id);
  }

  @Override
  public void updateIsActived(String id) throws Exception {
    begin();
    courseCategoryDao.updateIsActived(id);
    commit();
  }

}
