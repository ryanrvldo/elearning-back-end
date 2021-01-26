package com.lawencon.elearning.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseCategoryDao;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.service.CourseCategoryService;

/**
 * @author : Galih Dika Permana
 */
@Service
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
    setupUpdatedValue(courseCategory,
        () -> courseCategoryDao.getCategoryById(courseCategory.getId()));
    courseCategoryDao.updateCourseCategory(courseCategory, null);
  }

  @Override
  public void deleteCourseCategory(String id) throws Exception {
    courseCategoryDao.deleteCourseCategory(id);
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    begin();
    courseCategoryDao.updateIsActive(id, userId);
    commit();
  }

}
