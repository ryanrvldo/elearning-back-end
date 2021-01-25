package com.lawencon.elearning.service.impl;

import java.util.List;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseTypeDao;
import com.lawencon.elearning.dao.impl.CourseTypeDaoImpl;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.service.CourseTypeService;

/**
 * @author : Galih Dika Permana
 */

public class CourseTypeServiceImpl extends BaseServiceImpl implements CourseTypeService {

  private CourseTypeDao courseType = new CourseTypeDaoImpl();
  @Override
  public List<CourseType> getListCourseType() throws Exception {
    return courseType.getListCourseType();
  }

  @Override
  public void insertCourseType(CourseType courseType) throws Exception {
    this.courseType.insertCourseType(courseType, null);
  }

  @Override
  public void updateCourseType(CourseType courseType) throws Exception {
    this.courseType.updateCourseType(courseType, null);
  }

  @Override
  public void deleteCourseType(String id) throws Exception {
    courseType.deleteCourseType(id);
  }

  @Override
  public void updateIsActived(String id) throws Exception {
    try {
      begin();
      courseType.updateIsActived(id);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
