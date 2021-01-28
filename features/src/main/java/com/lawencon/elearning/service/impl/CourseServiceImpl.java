package com.lawencon.elearning.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.ModuleService;

/**
 * @author : Galih Dika Permana
 */
@Service
public class CourseServiceImpl extends BaseServiceImpl implements CourseService {

  @Autowired
  private CourseDao courseDao;
  @Autowired
  private ModuleService moduleService;

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
  public List<CourseResponseDTO> getCurrentAvailableCourse() throws Exception {
    List<Course> listCourse = courseDao.getCurrentAvailableCourse();
    return mergeData(listCourse);
  }

  @Override
  public List<CourseResponseDTO> getMyCourse(String id) throws Exception {
    List<Course> listCourse = courseDao.getMyCourse(id);
    return mergeData(listCourse);
  }

  @Override
  public List<CourseResponseDTO> getCourseForAdmin() throws Exception {
    List<Course> listCourse = courseDao.getCourseForAdmin();
    return mergeData(listCourse);
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    begin();
    courseDao.updateIsActive(id, userId);
    commit();
  }

  @Override
  public void registerCourse(String student, String course) throws Exception {
    begin();
    courseDao.registerCourse(course, student);
    commit();
  }

  public List<Module> getDetailCourse(String id) throws Exception {
    return moduleService.getDetailCourse(id);
  }

  private List<CourseResponseDTO> mergeData(List<Course> listCourse) {
    List<CourseResponseDTO> responseList = new ArrayList<>();
    listCourse.forEach(val -> {
      CourseResponseDTO courseDto = new CourseResponseDTO();
      courseDto.setCourseId(val.getId());
      courseDto.setCourseCode(val.getCode());
      courseDto.setTypeName(val.getCourseType().getName());
      courseDto.setCourseCapacity(val.getCapacity());
      courseDto.setCourseStatus(val.getStatus());
      courseDto.setCourseDescription(val.getDescription());
      courseDto.setCoursePeriodStart(val.getPeriodStart());
      courseDto.setCoursePeriodEnd(val.getPeriodEnd());
      courseDto.setTeacherId(val.getTeacher().getId());
      courseDto.setTeacherCode(val.getTeacher().getCode());
      courseDto.setUserFirstName(val.getTeacher().getUser().getFirstName());
      courseDto.setUserLastName(val.getTeacher().getUser().getLastName());
      courseDto.setTeacherTittle(val.getTeacher().getTitleDegree());
      courseDto.setCategoryCode(val.getCategory().getCode());
      courseDto.setCategoryName(val.getCategory().getName());
      responseList.add(courseDto);
    });
    return responseList;
  }
}
