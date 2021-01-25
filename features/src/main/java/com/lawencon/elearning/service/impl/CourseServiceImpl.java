package com.lawencon.elearning.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.service.CourseService;

/**
 * @author : Galih Dika Permana
 */

public class CourseServiceImpl extends BaseServiceImpl implements CourseService {

  @Autowired
  private CourseDao courseDao;

  @Override
  public List<Course> getListCourse() throws Exception {
    return courseDao.getListCourse();
  }

  @Override
  public String insertCourse(Course course) throws Exception {
    return courseDao.insertCourse(course, null);
  }

  @Override
  public void updateCourse(Course course) throws Exception {
    courseDao.updateCourse(course, null);
  }

  @Override
  public void deleteCourse(String id) throws Exception {
    courseDao.deleteCourse(id);
  }

  @Override
  public List<Course> getCurentAvailableCourse() throws Exception {
    return courseDao.getCurentAvailableCourse();
  }

  @Override
  public List<Course> getMyCourse() throws Exception {
    return courseDao.getMyCourse();
  }

  @Override
  public List<Course> getCourseForAdmin() throws Exception {
    return courseDao.getCourseForAdmin();
  }

  @Override
  public void updateIsActived(String id) throws Exception {
    begin();
    courseDao.updateIsActived(id);
    commit();
  }

  @Override
  public void registerCourse(Course course) throws Exception {
    courseDao.registerCourse(course);
  }

}
