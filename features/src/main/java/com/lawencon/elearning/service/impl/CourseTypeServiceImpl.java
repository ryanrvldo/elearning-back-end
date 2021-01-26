package com.lawencon.elearning.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseTypeDao;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.service.CourseTypeService;

/**
 * @author : Galih Dika Permana
 */
@Service
public class CourseTypeServiceImpl extends BaseServiceImpl implements CourseTypeService {

  @Autowired
  private CourseTypeDao courseTypeDao;

  @Override
  public List<CourseType> getListCourseType() throws Exception {
    return courseTypeDao.getListCourseType();
  }

  @Override
  public void insertCourseType(CourseType courseType) throws Exception {
    setupUpdatedValue(courseType, () -> courseTypeDao.getTypeById(courseType.getId()));
    courseTypeDao.insertCourseType(courseType, null);
  }

  @Override
  public void updateCourseType(CourseType courseType) throws Exception {
    courseTypeDao.updateCourseType(courseType, null);
  }

  @Override
  public void deleteCourseType(String id) throws Exception {
    courseTypeDao.deleteCourseType(id);
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
      begin();
    courseTypeDao.updateIsActive(id, userId);
      commit();
  }

}
