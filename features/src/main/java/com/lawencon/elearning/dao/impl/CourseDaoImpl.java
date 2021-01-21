package com.lawencon.elearning.dao.impl;

import java.util.List;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.model.Course;

/**
 * @author : Galih Dika Permana
 */

public class CourseDaoImpl extends BaseDaoImpl<Course> implements CourseDao, BaseCustomDao {

  @Override
  public List<Course> getListCourse() throws Exception {
    return getAll();
  }

  @Override
  public void insertCourse(Course course) throws Exception {
    save(course, null, null, true, true);
  }

  @Override
  public void updateCourse(Course course) throws Exception {
    save(course, null, null, true, true);
  }

  @Override
  public void deleteCourse(String id) throws Exception {
    deleteById(id);
  }

}
