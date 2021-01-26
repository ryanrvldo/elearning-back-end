package com.lawencon.elearning.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.service.CourseService;

/**
 * @author : Galih Dika Permana
 */
@Service
public class CourseServiceImpl extends BaseServiceImpl implements CourseService {

  @Autowired
  private CourseDao courseDao;

  @Override
  public List<Course> getListCourse() throws Exception {
    return courseDao.getListCourse();
  }

  @Override
  public void insertCourse(Course course) throws Exception {
    courseDao.insertCourse(course, null);
  }

  @Override
  public void updateCourse(Course course) throws Exception {
    setupUpdatedValue(course, () -> courseDao.getCourseById(course.getId()));
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
  public void updateIsActive(String id, String userId) throws Exception {
    begin();
    courseDao.updateIsActive(id, userId);
    commit();
  }

  @Override
  public void registerCourse(Course course) throws Exception {
    courseDao.registerCourse(course);
  }

}
