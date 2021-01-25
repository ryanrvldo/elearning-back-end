package com.lawencon.elearning.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseTypeDao;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.service.CourseTypeService;

/**
 * @author : Galih Dika Permana
 */

public class CourseTypeServiceImpl extends BaseServiceImpl implements CourseTypeService {

  @Autowired
  private CourseTypeDao courseTypeDao;

  @Override
  public List<CourseType> getListCourseType() throws Exception {
    return courseTypeDao.getListCourseType();
  }

  @Override
  public void insertCourseType(CourseType courseType) throws Exception {
    this.courseTypeDao.insertCourseType(courseType, null);
  }

  @Override
  public void updateCourseType(CourseType courseType) throws Exception {
    this.courseTypeDao.updateCourseType(courseType, null);
  }

  @Override
  public void deleteCourseType(String id) throws Exception {
    courseTypeDao.deleteCourseType(id);
  }

  @Override
  public void updateIsActived(String id) throws Exception {
      begin();
      courseTypeDao.updateIsActived(id);
      commit();
  }

}
