package com.lawencon.elearning.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dto.course.CourseCreateRequestDTO;
import com.lawencon.elearning.dto.course.CourseDeleteRequestDTO;
import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.course.CourseUpdateRequestDTO;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.CourseStatus;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.ModuleService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author : Galih Dika Permana
 */
@Service
public class CourseServiceImpl extends BaseServiceImpl implements CourseService {

  @Autowired
  private CourseDao courseDao;
  @Autowired
  private ModuleService moduleService;

  @Autowired
  private StudentService studentService;

  @Autowired
  private ValidationUtil validateUtil;

  @Override
  public List<Course> getListCourse() throws Exception {
    List<Course> listCourse = courseDao.getListCourse();
    if (listCourse.isEmpty()) {
      throw new DataIsNotExistsException("Data is not exist");
    }
    return listCourse;
  }

  @Override
  public void insertCourse(CourseCreateRequestDTO courseDTO) throws Exception {
    validateUtil.validate(courseDTO);
    Course course = new Course();
    course.setCapacity(courseDTO.getCapacity());
    course.setCode(courseDTO.getCode());
    course.setCreatedBy(courseDTO.getCreatedBy());
    course.setDescription(courseDTO.getDescription());

    course.setPeriodStart(courseDTO.getPeriodStart());
    course.setPeriodEnd(courseDTO.getPeriodEnd());

    course.setStatus(CourseStatus.valueOf(courseDTO.getStatus()));

    CourseType courseType = new CourseType();
    courseType.setId(courseDTO.getCourseTypeId());
    course.setCourseType(courseType);

    CourseCategory courseCategory = new CourseCategory();
    courseCategory.setId(courseDTO.getCourseCategoryId());
    course.setCategory(courseCategory);

    Teacher teacher = new Teacher();
    teacher.setId(courseDTO.getTeacherId());
    course.setTeacher(teacher);
    courseDao.insertCourse(course, null);
  }

  @Override
  public void updateCourse(CourseUpdateRequestDTO courseDTO) throws Exception {
    validateUtil.validate(courseDTO);
    Course course = new Course();
    course.setId(courseDTO.getId());
    course.setCapacity(courseDTO.getCapacity());
    course.setCode(courseDTO.getCode());
    course.setUpdatedBy(courseDTO.getUpdateBy());
    course.setDescription(courseDTO.getDescription());

    course.setPeriodStart(courseDTO.getPeriodStart());
    course.setPeriodEnd(courseDTO.getPeriodEnd());

    course.setStatus(CourseStatus.valueOf(courseDTO.getStatus()));

    CourseType courseType = new CourseType();
    courseType.setId(courseDTO.getCourseTypeId());
    course.setCourseType(courseType);

    CourseCategory courseCategory = new CourseCategory();
    courseCategory.setId(courseDTO.getCourseCategoryId());
    course.setCategory(courseCategory);

    Teacher teacher = new Teacher();
    teacher.setId(courseDTO.getTeacherId());
    course.setTeacher(teacher);
    Course courseDaoModel = courseDao.getCourseById(course.getId());
    if (courseDaoModel == null) {
      throw new DataIsNotExistsException("id", course.getId());
    }
    setupUpdatedValue(course, () -> courseDaoModel);

    courseDao.updateCourse(course, null);
  }

  @Override
  public void deleteCourse(CourseDeleteRequestDTO courseDTO) throws Exception {
    try {
      begin();
      courseDao.deleteCourse(courseDTO.getId());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (e.getMessage().equals("ID Not Found")) {
        throw new DataIsNotExistsException("Id" + courseDTO.getId());
      }
      begin();
      updateIsActive(courseDTO.getId(), courseDTO.getUpdateBy());
      commit();
    }

  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    courseDao.updateIsActive(id, userId);
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
  public void registerCourse(String student, String course) throws Exception {
    begin();
    courseDao.registerCourse(course, student);
    commit();
  }

  public List<ModuleResponseDTO> getDetailCourse(String id) throws Exception {
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

  @Override
  public List<StudentByCourseResponseDTO> getStudentByCourseId(String id) throws Exception {
    Course course = courseDao.getCourseById(id);
    if (course == null) {
      throw new DataIsNotExistsException("id", id);
    }
    List<StudentByCourseResponseDTO> listStudent = studentService.getListStudentByIdCourse(id);
    if (listStudent.isEmpty()) {
      throw new DataIsNotExistsException("Data is empty");
    }
    return listStudent;

  }
}
