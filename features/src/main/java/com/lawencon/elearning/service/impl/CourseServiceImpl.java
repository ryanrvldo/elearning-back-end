package com.lawencon.elearning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dto.course.CourseAdminResponseDTO;
import com.lawencon.elearning.dto.course.CourseCreateRequestDTO;
import com.lawencon.elearning.dto.course.CourseDeleteRequestDTO;
import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.course.CourseUpdateRequestDTO;
import com.lawencon.elearning.dto.course.DetailCourseResponseDTO;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import com.lawencon.elearning.dto.teacher.TeacherForAvailableCourseDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.CourseStatus;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.File;
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
  private StudentService studentService;

  @Autowired
  private ModuleService moduleService;

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

    course.setStatus(CourseStatus.REGISTRATION);

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
    if (listCourse.isEmpty()) {
      throw new DataIsNotExistsException("Data is empty");
    }
    List<CourseResponseDTO> responseList = new ArrayList<>();
    for (int i = 0; i < listCourse.size(); i++) {
      CourseResponseDTO courseDto = new CourseResponseDTO();
      courseDto.setId(listCourse.get(i).getId());
      courseDto.setCode(listCourse.get(i).getCode());
      courseDto.setTypeName(listCourse.get(i).getCourseType().getName());
      courseDto.setCapacity(listCourse.get(i).getCapacity());
      courseDto.setCourseStatus(listCourse.get(i).getStatus());
      courseDto.setCourseDescription(listCourse.get(i).getDescription());
      courseDto.setPeriodStart(listCourse.get(i).getPeriodStart());
      courseDto.setPeriodEnd(listCourse.get(i).getPeriodEnd());

      TeacherForAvailableCourseDTO teacherDTO = new TeacherForAvailableCourseDTO();
      teacherDTO.setId(listCourse.get(i).getTeacher().getId());
      teacherDTO.setCode(listCourse.get(i).getTeacher().getCode());
      teacherDTO.setFirstName(listCourse.get(i).getTeacher().getUser().getFirstName());
      teacherDTO.setLastName(listCourse.get(i).getTeacher().getUser().getLastName());
      teacherDTO.setTittle(listCourse.get(i).getTeacher().getTitleDegree());
      File teacherPhoto = listCourse.get(i).getTeacher().getUser().getUserPhoto();
      if (teacherPhoto == null || teacherPhoto.getId() == null) {
        teacherDTO.setPhotoId("");
      } else {
        teacherDTO.setPhotoId(listCourse.get(i).getTeacher().getUser().getUserPhoto().getId());
      }
      courseDto.setTeacher(teacherDTO);
      courseDto.setCategoryName(listCourse.get(i).getCategory().getName());
      Integer availableCapacity = courseDao.getCapacityCourse(courseDto.getId());
      if (courseDto.getCapacity() > availableCapacity) {
        responseList.add(courseDto);
      }
    }
    return responseList;
  }

  @Override
  public List<CourseResponseDTO> getCourseByStudentId(String id) throws Exception {
    List<Course> listCourse = courseDao.getCourseByStudentId(id);
    if (listCourse.isEmpty()) {
      throw new DataIsNotExistsException("Data is empty");
    }
    List<CourseResponseDTO> responseList = new ArrayList<>();
    listCourse.forEach(val -> {
      CourseResponseDTO courseDto = new CourseResponseDTO();
      courseDto.setId(val.getId());
      courseDto.setCode(val.getCode());
      courseDto.setTypeName(val.getCourseType().getName());
      courseDto.setCapacity(val.getCapacity());
      courseDto.setCourseStatus(val.getStatus());
      courseDto.setCourseDescription(val.getDescription());
      courseDto.setPeriodStart(val.getPeriodStart());
      courseDto.setPeriodEnd(val.getPeriodEnd());

      TeacherForAvailableCourseDTO teacherDTO = new TeacherForAvailableCourseDTO();
      teacherDTO.setId(val.getTeacher().getId());
      teacherDTO.setCode(val.getTeacher().getCode());
      teacherDTO.setFirstName(val.getTeacher().getUser().getFirstName());
      teacherDTO.setLastName(val.getTeacher().getUser().getLastName());
      teacherDTO.setTittle(val.getTeacher().getTitleDegree());
      File teacherPhoto = val.getTeacher().getUser().getUserPhoto();
      if (teacherPhoto == null || teacherPhoto.getId() == null) {
        teacherDTO.setPhotoId("");
      } else {
        teacherDTO.setPhotoId(val.getTeacher().getUser().getUserPhoto().getId());
      }
      courseDto.setTeacher(teacherDTO);
      courseDto.setCategoryName(val.getCategory().getName());
      responseList.add(courseDto);
    });
    return responseList;
  }

  @Override
  public List<CourseAdminResponseDTO> getCourseForAdmin() throws Exception {
    List<Course> listCourse = courseDao.getCourseForAdmin();
    List<CourseAdminResponseDTO> listResult = new ArrayList<>();
    if (listCourse.isEmpty()) {
      throw new DataIsNotExistsException("Data is empty");
    }
    listCourse.forEach(val -> {
      CourseAdminResponseDTO data = new CourseAdminResponseDTO();
      data.setId(val.getId());
      data.setCode(val.getCode());
      data.setCategoryName(val.getCategory().getName());
      data.setTypeName(val.getCourseType().getName());
      data.setCapacity(val.getCapacity());
      data.setPeriodStart(val.getPeriodStart());
      data.setStatus(val.getStatus().toString());
      data.setPeriodEnd(val.getPeriodEnd());
      data.setDescription(val.getDescription());
      listResult.add(data);
    });
    return listResult;
  }


  @Override
  public void registerCourse(String studentId, String courseId) throws Exception {
    begin();
    validateNullId(studentId, "id");
    validateNullId(courseId, "id");
    courseDao.registerCourse(courseId, studentId);
    commit();
  }

  @Override
  public DetailCourseResponseDTO getDetailCourse(String courseId, String studentId)
      throws Exception {
    validateNullId(courseId, "id");
    DetailCourseResponseDTO detailDTO = new DetailCourseResponseDTO();
    setData(courseId, detailDTO, studentId);
    return detailDTO;
  }

  @Override
  public List<StudentByCourseResponseDTO> getStudentByCourseId(String id) throws Exception {
    validateNullId(id, "id");
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

  @Override
  public Map<Course, Integer[]> getTeacherCourse(String id) throws Exception {
    validateNullId(id, "id");
    Map<Course, Integer[]> listCourse = courseDao.getTeacherCourse(id);
    if (listCourse.isEmpty()) {
      throw new DataIsNotExistsException("Data is empty");
    }
    return listCourse;
  }

  @Override
  public Course getCourseById(String id) throws Exception {
    validateNullId(id, "id");
    return courseDao.getCourseById(id);
  }

  private void setData(String courseId, DetailCourseResponseDTO detailDTO, String studentId)
      throws Exception {
    Course course = courseDao.getCourseById(courseId);
    List<ModuleResponseDTO> listModule = new ArrayList<>();
    listModule = moduleService.getModuleListByIdCourse(courseId, "");
    detailDTO.setId(course.getId());
    detailDTO.setCode(course.getCode());
    detailDTO.setName(course.getCourseType().getName());
    detailDTO.setCapacity(course.getCapacity());
    detailDTO.setTotalStudent(courseDao.getTotalStudentByIdCourse(courseId));
    detailDTO.setDescription(course.getDescription());
    detailDTO.setPeriodStart(course.getPeriodStart());
    detailDTO.setPeriodEnd(course.getPeriodEnd());
    detailDTO.setModules(listModule);
    if (studentId != null) {
      setDataWithStudentId(courseId, detailDTO, studentId);
    }
  }

  private void setDataWithStudentId(String courseId, DetailCourseResponseDTO detailDTO,
      String studentId) throws Exception {
    List<ModuleResponseDTO> listModule = new ArrayList<>();
    listModule = moduleService.getModuleListByIdCourse(courseId, studentId);
    detailDTO.setModules(listModule);
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
