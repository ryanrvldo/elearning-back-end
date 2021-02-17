package com.lawencon.elearning.service.impl;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dto.EmailSetupDTO;
import com.lawencon.elearning.dto.admin.DashboardCourseResponseDto;
import com.lawencon.elearning.dto.course.CourseAdminResponseDTO;
import com.lawencon.elearning.dto.course.CourseCreateRequestDTO;
import com.lawencon.elearning.dto.course.CourseDeleteRequestDTO;
import com.lawencon.elearning.dto.course.CourseProgressResponseDTO;
import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.course.CourseUpdateRequestDTO;
import com.lawencon.elearning.dto.course.DashboardCourseResponseDTO;
import com.lawencon.elearning.dto.course.DetailCourseResponseDTO;
import com.lawencon.elearning.dto.experience.ExperienceResponseDto;
import com.lawencon.elearning.dto.module.ModuleListReponseDTO;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.dto.teacher.TeacherForAvailableCourseDTO;
import com.lawencon.elearning.error.DataAlreadyExistException;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.CourseStatus;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.GeneralCode;
import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.ExperienceService;
import com.lawencon.elearning.service.GeneralService;
import com.lawencon.elearning.service.ModuleService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.MailUtils;
import com.lawencon.elearning.util.ValidationUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  @Autowired
  private ExperienceService experienceService;

  @Autowired
  private UserService userService;

  @Autowired
  private MailUtils mailUtils;

  @Autowired
  private GeneralService generalService;

  @Override
  public List<CourseResponseDTO> getAllCourse() throws Exception {
    List<Course> listCourse = courseDao.findAll();
    if (listCourse.isEmpty()) {
      throw new DataIsNotExistsException("Data is not exist");
    }
    return getAndSetupCourseResponse(listCourse, null);
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
    User user = userService.getById(courseDTO.getUpdateBy());
    if (!user.getRole().getCode().equals(Roles.ADMIN.getCode()) && !user.getIsActive()) {
      throw new IllegalAccessException("only admin can update data !");
    }
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
    validateUtil.validate(courseDTO);
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
  public List<CourseResponseDTO> getCurrentAvailableCourse(String studentId) throws Exception {
    List<Course> listCourseAvailable = courseDao.getCurrentAvailableCourse();
    List<Course> listCourseByStudent = courseDao.getCourseByStudentId(studentId);
    if (listCourseAvailable.isEmpty()) {
      throw new DataIsNotExistsException("Data is empty");
    }
    return getAndSetupCourseResponse(listCourseAvailable, (course, response) -> {
      response.setIsRegist(false);
      listCourseByStudent.forEach(c -> {
        if (course.getId().equals(c.getId())) {
          response.setIsRegist(true);
        }
      });
    });
  }

  @Override
  public List<CourseResponseDTO> getCourseByStudentId(String id) throws Exception {
    validateUtil.validateUUID(id);
    studentService.getStudentById(id);
    List<Course> listCourse = courseDao.getCourseByStudentId(id);
    List<CourseProgressResponseDTO> progressList = courseDao.getCourseProgressByStudentId(id);
    if (listCourse.isEmpty()) {
      throw new DataIsNotExistsException("You haven't select any course");
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
      teacherDTO.setTitle(val.getTeacher().getTitleDegree());
      File teacherPhoto = val.getTeacher().getUser().getUserPhoto();
      if (teacherPhoto == null || teacherPhoto.getId() == null) {
        teacherDTO.setPhotoId("");
      } else {
        teacherDTO.setPhotoId(val.getTeacher().getUser().getUserPhoto().getId());
      }
      courseDto.setTeacher(teacherDTO);
      courseDto.setCategoryName(val.getCategory().getName());

      progressList.stream()
          .filter(p -> p.getCourseId().equals(val.getId()))
          .findFirst()
          .ifPresent(p -> {
            courseDto.setTotalModule(Optional.of(p.getTotalModule())
                .orElse(0));
            if (courseDto.getTotalModule().equals(0)) {
              courseDto.setModuleComplete(0);
              courseDto.setPercentProgress(0.0);
              return;
            }
            Integer moduleComplete = 0;
            try {
              moduleComplete = courseDao.getModuleCompleteByStudentId(p.getCourseId(), id);
            } catch (Exception e) {
              e.printStackTrace();
            }
            courseDto.setModuleComplete(moduleComplete);
            courseDto.setPercentProgress(
                ((double) courseDto.getModuleComplete() / (double) courseDto.getTotalModule())
                    * 100);
          });
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
      data.setTypeId(val.getCourseType().getId());
      data.setCategoryId(val.getCategory().getId());
      data.setTeacherId(val.getTeacher().getId());
      listResult.add(data);
    });
    return listResult;
  }


  @Override
  public void registerCourse(String studentId, String courseId) throws Exception {
    validateUtil.validateUUID(studentId, courseId);
    studentService.getStudentById(studentId);
    Course course = courseDao.getCourseById(courseId);
    if (course == null) {
      throw new DataIsNotExistsException("course id", courseId);
    }
    if (LocalDateTime.now().isAfter(course.getPeriodStart())) {
      throw new IllegalRequestException("can't register to course when course already on going");
    }
    Integer count = courseDao.checkDataRegisterCourse(courseId, studentId);
    if (count == 0) {
      Integer capacityRegist = courseDao.getCapacityCourse(courseId);
      if (capacityRegist < course.getCapacity()) {
        try {
          begin();
          courseDao.registerCourse(courseId, studentId);

          String template = generalService.getTemplateHTML(GeneralCode.REGISTER_COURSE.getCode());

          String[] emailTo = {"lawerning.acc@gmail.com"};
          EmailSetupDTO email = new EmailSetupDTO();
          email.setTo(emailTo);
          email.setSubject("Register Course Success!");
          email.setBody(template);
          new EmailServiceImpl(mailUtils, email).start();
          commit();

        } catch (Exception e) {
          e.printStackTrace();
          rollback();
          throw e;
        }
      } else {
        throw new IllegalRequestException("capacity already full");
      }
    } else {
      throw new DataAlreadyExistException("student id", studentId);
    }


  }

  @Override
  public DetailCourseResponseDTO getDetailCourse(String courseId, String studentId)
      throws Exception {
    validateUtil.validateUUID(courseId);
    DetailCourseResponseDTO detailDTO = new DetailCourseResponseDTO();
    setData(courseId, detailDTO, studentId);
    return detailDTO;
  }

  @Override
  public DashboardCourseResponseDTO getCourseForDashboard(String courseId) throws Exception {
    validateUtil.validateUUID(courseId);
    DashboardCourseResponseDTO detailDTO = new DashboardCourseResponseDTO();
    Course course = courseDao.getCourseById(courseId);
    if (course == null) {
      throw new DataIsNotExistsException("course id", courseId);
    }
    List<ModuleListReponseDTO> listModule = moduleService.getModuleList(courseId);
    detailDTO.setId(course.getId());
    detailDTO.setCode(course.getCode());
    detailDTO.setName(course.getCourseType().getName());
    detailDTO.setCapacity(course.getCapacity());
    detailDTO.setTotalStudent(courseDao.getTotalStudentByIdCourse(courseId));
    detailDTO.setDescription(course.getDescription());
    detailDTO.setPeriodStart(course.getPeriodStart());
    detailDTO.setPeriodEnd(course.getPeriodEnd());
    detailDTO.setModules(listModule);
    return detailDTO;
  }

  @Override
  public List<StudentByCourseResponseDTO> getStudentByCourseId(String id) throws Exception {
    validateUtil.validateUUID(id);
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
    validateUtil.validateUUID(id);
    Map<Course, Integer[]> listCourse = courseDao.getTeacherCourse(id);
    if (listCourse.isEmpty()) {
      throw new DataIsNotExistsException("Data is empty");
    }
    return listCourse;
  }

  @Override
  public Course getCourseById(String id) throws Exception {
    validateUtil.validateUUID(id);
    Course course = courseDao.getCourseById(id);
    validateUtil.validate(course);
    return course;
  }

  private void setData(String courseId, DetailCourseResponseDTO detailDTO, String studentId)
      throws Exception {
    Course course = courseDao.getCourseById(courseId);
    List<ModuleResponseDTO> listModule = moduleService.getModuleListByIdCourse(courseId, "");
    detailDTO.setId(course.getId());
    detailDTO.setCode(course.getCode());
    detailDTO.setName(course.getCourseType().getName());
    detailDTO.setCapacity(course.getCapacity());
    detailDTO.setTotalStudent(courseDao.getTotalStudentByIdCourse(courseId));
    detailDTO.setDescription(course.getDescription());
    detailDTO.setPeriodStart(course.getPeriodStart());
    detailDTO.setPeriodEnd(course.getPeriodEnd());
    detailDTO.setTeacherFirstName(course.getTeacher().getUser().getFirstName());
    detailDTO.setTeacherLastName(course.getTeacher().getUser().getLastName());
    detailDTO.setIdPhoto(course.getTeacher().getUser().getUserPhoto().getId());
    detailDTO.setModules(listModule);
    if (studentId != null) {
      setDataWithStudentId(courseId, detailDTO, studentId);
    }
  }

  private void setDataWithStudentId(String courseId, DetailCourseResponseDTO detailDTO,
      String studentId) throws Exception {
    List<ModuleResponseDTO> listModule = moduleService.getModuleListByIdCourse(courseId, studentId);
    detailDTO.setModules(listModule);
  }

  private List<CourseResponseDTO> getAndSetupCourseResponse(List<Course> courseList,
      BiConsumer<Course, CourseResponseDTO> courseConsumer) throws Exception {
    List<CourseResponseDTO> responseList = new ArrayList<>();
    for (Course course : courseList) {
      CourseResponseDTO courseResponse = new CourseResponseDTO();
      if (courseConsumer != null) {
        courseConsumer.accept(course, courseResponse);
      }
      courseResponse.setId(course.getId());
      courseResponse.setCode(course.getCode());
      courseResponse.setTypeName(course.getCourseType().getName());
      courseResponse.setCapacity(course.getCapacity());
      courseResponse.setCourseStatus(course.getStatus());
      courseResponse.setCourseDescription(course.getDescription());
      courseResponse.setPeriodStart(course.getPeriodStart());
      courseResponse.setPeriodEnd(course.getPeriodEnd());

      Teacher teacher = course.getTeacher();
      TeacherForAvailableCourseDTO teacherResponse = new TeacherForAvailableCourseDTO();
      teacherResponse.setId(teacher.getId());
      teacherResponse.setCode(teacher.getCode());
      teacherResponse.setFirstName(teacher.getUser().getFirstName());
      teacherResponse.setLastName(teacher.getUser().getLastName());
      teacherResponse.setTitle(teacher.getTitleDegree());

      List<ExperienceResponseDto> experience = experienceService.getAllByTeacherId(teacher.getId());
      if (experience != null) {
        teacherResponse.setExperience(experience.get(experience.size() - 1).getTitle());
      }

      File teacherPhoto = teacher.getUser().getUserPhoto();
      teacherResponse.setPhotoId(teacherPhoto.getId());

      courseResponse.setTeacher(teacherResponse);
      courseResponse.setCategoryCode(course.getCategory().getCode());
      courseResponse.setCategoryName(course.getCategory().getName());
      responseList.add(courseResponse);
    }
    return responseList;
  }

  @Override
  public DashboardCourseResponseDto dashboardCourseByAdmin() throws Exception {
    DashboardCourseResponseDto dashboardCourse = courseDao.dashboardCourseByAdmin();
    dashboardCourse.setRegisteredStudent(courseDao.getRegisterStudent());
    return dashboardCourse;
  }

  @Override
  public List<CourseAttendanceReportByTeacher> getCourseAttendanceReport(String courseId)
      throws Exception {
    validateUtil.validateUUID(courseId);
    Course course = courseDao.getCourseById(courseId);
    if (course == null) {
      throw new DataIsNotExistsException("course id", courseId);
    }
    List<CourseAttendanceReportByTeacher> listData = courseDao.getCourseAttendanceReport(courseId);
    if (listData.isEmpty()) {
      throw new DataIsNotExistsException("Data empty");
    }
    listData.forEach(val -> {
      try {
        Integer totalStudent = courseDao.getTotalStudentByIdCourse(courseId);
        val.setTotalStudent(totalStudent);
        val.setAbsent(totalStudent - val.getPresent());

      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    return listData;
  }

  @Override
  public Integer getRegisterStudent() throws Exception {
    return courseDao.getRegisterStudent();
  }

  @Override
  public List<CourseProgressResponseDTO> getCourseProgressByStudentId(String studentId)
      throws Exception {
    validateUtil.validateUUID(studentId);
    studentService.getStudentById(studentId);
    List<CourseProgressResponseDTO> listCourse = courseDao.getCourseProgressByStudentId(studentId);
    if (listCourse.isEmpty()) {
      throw new DataIsNotExistsException("student id", studentId);
    }
    listCourse.forEach(val -> {
      if (val.getTotalModule() == null || val.getTotalModule() == 0) {
        val.setModuleComplete(0);
        val.setPercentProgress(0.0);
        return;
      }
      Integer moduleComplete = 0;
      try {
        moduleComplete = courseDao.getModuleCompleteByStudentId(val.getCourseId(), studentId);
      } catch (Exception e) {
        e.printStackTrace();
      }
      val.setModuleComplete(moduleComplete);
      val.setPercentProgress(
          ((double) val.getModuleComplete() / (double) val.getTotalModule()) * 100);
    });
    return listCourse;
  }

}
