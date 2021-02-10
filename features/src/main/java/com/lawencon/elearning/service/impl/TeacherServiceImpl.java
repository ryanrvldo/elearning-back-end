package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.TeacherDao;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.experience.ExperienceResponseDto;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.dto.teacher.DashboardTeacherDTO;
import com.lawencon.elearning.dto.teacher.TeacherForAdminDTO;
import com.lawencon.elearning.dto.teacher.TeacherProfileDTO;
import com.lawencon.elearning.dto.teacher.TeacherReportResponseDTO;
import com.lawencon.elearning.dto.teacher.TeacherRequestDTO;
import com.lawencon.elearning.dto.teacher.UpdateTeacherRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.ExperienceService;
import com.lawencon.elearning.service.ModuleService;
import com.lawencon.elearning.service.RoleService;
import com.lawencon.elearning.service.TeacherService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author Dzaky Fadhilla Guci
 */

@Service
public class TeacherServiceImpl extends BaseServiceImpl implements TeacherService {

  @Autowired
  private TeacherDao teacherDao;

  @Autowired
  private ExperienceService experienceService;

  @Autowired
  private RoleService roleService;

  @Autowired
  private UserService userService;

  @Autowired
  private ValidationUtil validUtil;

  @Autowired
  private CourseService courseService;

  @Autowired
  private ModuleService moduleService;

  @Override
  public List<TeacherForAdminDTO> getAllTeachers() throws Exception {
    List<Teacher> teacherList = teacherDao.getAllTeachers();
    if (teacherList.isEmpty()) {
      throw new DataIsNotExistsException("There is no teacher yet.");
    }

    List<TeacherForAdminDTO> responseList = new ArrayList<>();
    teacherList.forEach(teacher -> {
      responseList.add(new TeacherForAdminDTO(
          teacher.getId(),
          teacher.getCode(),
          teacher.getUser().getUsername(),
          teacher.getUser().getFirstName(),
          teacher.getUser().getLastName(),
          teacher.getTitleDegree(),
          teacher.getUser().getEmail(),
          teacher.getPhone(),
          teacher.getGender(),
          teacher.getUser().getUserPhoto().getId(),
          teacher.getIsActive()));
    });
    return responseList;
  }

  @Override
  public List<TeacherForAdminDTO> allTeachersForAdmin() throws Exception {
    List<TeacherForAdminDTO> listTeachers = Optional.ofNullable(teacherDao.allTeachersForAdmin())
        .orElseThrow(() -> new DataIsNotExistsException("Teachers data have not been registered "));
    return listTeachers;
  }

  @Override
  public void saveTeacher(TeacherRequestDTO data) throws Exception {
    validUtil.validate(data);
    User user = new User();
    user.setFirstName(data.getFirstName());
    user.setLastName(data.getLastName());
    user.setUsername(data.getUsername());
    user.setPassword(data.getPassword());
    user.setEmail(data.getEmail());
    user.setCreatedBy(data.getCreatedBy());

    Role role = roleService.findByCode(Roles.TEACHER.getCode());
    user.setRole(role);

    Teacher teacher = new Teacher();
    teacher.setCode(data.getCode());
    teacher.setPhone(data.getPhone());
    teacher.setGender(data.getGender());
    teacher.setTitleDegree(data.getTitleDegree());
    teacher.setCreatedAt(LocalDateTime.now());
    teacher.setCreatedBy(data.getCreatedBy());

    try {
      begin();
      userService.addUser(user);
      teacher.setUser(user);
      teacherDao.saveTeacher(teacher, null);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public Teacher findTeacherById(String id) throws Exception {
    validateNullId(id, "Id");
    return teacherDao.findTeacherById(id);
  }

  @Override
  public TeacherProfileDTO findTeacherByIdCustom(String id) throws Exception {
    validateNullId(id, "Id");
    validUtil.validateUUID(id);
    TeacherProfileDTO teacher = Optional.ofNullable(teacherDao.findTeacherByIdCustom(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));

    List<ExperienceResponseDto> experiences = experienceService.getAllByTeacherId(id);
    teacher.setExperiences(experiences);
    return teacher;
  }

  @Override
  public void updateTeacher(UpdateTeacherRequestDTO data) throws Exception {
    validUtil.validate(data);

    User userValidate = Optional.ofNullable(userService.getById(data.getUpdatedBy()))
        .orElseThrow(() -> new DataIsNotExistsException("id", data.getUpdatedBy()));

    if (!(userValidate.getRole().getCode().equals(Roles.SUPER_ADMIN.getCode())
        || userValidate.getRole().getCode().equals(Roles.ADMIN.getCode())
        || (userValidate.getRole().getCode().equals(Roles.TEACHER.getCode())))) {
      throw new IllegalAccessException("Role not match");
    }

    Teacher teacherDB = Optional.ofNullable(findTeacherById(data.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("id", data.getId()));

    teacherDB.setPhone(data.getPhone());
    teacherDB.setTitleDegree(data.getTitleDegree());
    teacherDB.setGender(data.getGender());
    teacherDB.setUpdatedBy(data.getUpdatedBy());
    teacherDB.setUpdatedAt(LocalDateTime.now());

    User user = new User();
    user.setId(teacherDB.getUser().getId());
    user.setUsername(data.getUsername());
    user.setFirstName(data.getFirstName());
    user.setLastName(data.getLastName());
    user.setUpdatedBy(data.getUpdatedBy());

    try {
      begin();
      userService.updateUser(user);
      teacherDao.updateTeacher(teacherDB, null);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public Teacher getFullNameByUserId(String userId) throws Exception {
    validateNullId(userId, "User Id");
    return Optional.ofNullable(teacherDao.findByUserId(userId))
        .orElseThrow(() -> new DataIsNotExistsException("User Id", userId));
  }

  @Override
  public void deleteTeacherById(String teacherId) throws Exception {
    validateNullId(teacherId, "Id");
    validUtil.validateUUID(teacherId);

    try {
      begin();
      teacherDao.deleteTeacherById(teacherId);
      String idUser = teacherDao.getUserId(teacherId);
      if (idUser != null) {
        userService.deleteById(idUser);
      }
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public void updateIsActive(UpdateIsActiveRequestDTO deleteReq) throws Exception {
    validUtil.validate(deleteReq);
    try {
      begin();
      teacherDao.updateIsActive(deleteReq.getId(), deleteReq.getUpdatedBy(), deleteReq.getStatus());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public Teacher findByIdForCourse(String id) throws Exception {
    validateNullId(id, "Id");
    return teacherDao.findByIdForCourse(id);
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

  @Override
  public List<DashboardTeacherDTO> getTeacherDashboard(String id) throws Exception {
    Map<Course, Integer[]> resultMap = courseService.getTeacherCourse(id);
    List<DashboardTeacherDTO> listResult = new ArrayList<>();
    resultMap.keySet().forEach(course -> {
      DashboardTeacherDTO dashboard = new DashboardTeacherDTO();
      dashboard.setId(course.getId());
      dashboard.setCode(course.getCode());
      dashboard.setName(course.getCourseType().getName());
      dashboard.setDescription(course.getDescription());
      dashboard.setCapacity(course.getCapacity());
      dashboard.setTotalStudent(resultMap.get(course)[0]);
      dashboard.setTotalModule(resultMap.get(course)[1]);
      dashboard.setPeriodEnd(course.getPeriodEnd());
      dashboard.setPeriodStart(course.getPeriodStart());
      listResult.add(dashboard);
    });
    return listResult;
  }

  @Override
  public List<TeacherReportResponseDTO> getTeacherDetailCourseReport(String moduleId)
      throws Exception {
    validUtil.validateUUID(moduleId);
    Module module = moduleService.getModuleById(moduleId);
    if (module == null) {
      throw new DataIsNotExistsException("module id", moduleId);
    }
    List<TeacherReportResponseDTO> listResult = teacherDao.getTeacherDetailCourseReport(moduleId);
    if (listResult.isEmpty()) {
      throw new DataIsNotExistsException("Data empty");
    }
    return listResult;
  }

  @Override
  public List<CourseAttendanceReportByTeacher> getCourseAttendanceReport(String courseId)
      throws Exception {
    validUtil.validateUUID(courseId);
    Course course = courseService.getCourseById(courseId);
    if (course == null) {
      throw new DataIsNotExistsException("course id", courseId);
    }
    List<CourseAttendanceReportByTeacher> listData =
        teacherDao.getCourseAttendanceReport(courseId);
    if (listData.isEmpty()) {
      throw new DataIsNotExistsException("Data empty");
    }
    listData.forEach(val -> {
      try {
        Integer totalStudent = teacherDao.getTotalStudentByIdCourse(courseId);
        val.setTotalStudent(totalStudent);
        val.setAbsent(totalStudent - val.getPresent());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    return listData;
  }

  @Override
  public Course getCourseById(String courseId) throws Exception {
    return courseService.getCourseById(courseId);
  }

  @Override
  public Integer countTotalTeacher() throws Exception {
    return teacherDao.countTotalTeacher();
  }

}
