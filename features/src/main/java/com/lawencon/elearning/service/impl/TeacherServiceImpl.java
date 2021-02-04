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
import com.lawencon.elearning.dto.experience.ExperienceResponseDto;
import com.lawencon.elearning.dto.teacher.DashboardTeacherDTO;
import com.lawencon.elearning.dto.teacher.DeleteTeacherDTO;
import com.lawencon.elearning.dto.teacher.TeacherForAdminDTO;
import com.lawencon.elearning.dto.teacher.TeacherProfileDTO;
import com.lawencon.elearning.dto.teacher.TeacherRequestDTO;
import com.lawencon.elearning.dto.teacher.UpdateTeacherRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.ExperienceService;
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
  private UserService userService;

  @Autowired
  private ValidationUtil validUtil;

  @Autowired
  private CourseService courseService;


  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    teacherDao.updateIsActive(id, userId);
  }

  @Override
  public List<Teacher> getAllTeachers() throws Exception {
    return Optional.ofNullable(teacherDao.getAllTeachers())
        .orElseThrow(() -> new DataIsNotExistsException("Teachers data have not been registered "));
  }

  @Override
  public List<TeacherForAdminDTO> allTeachersForAdmin() throws Exception {
    List<Teacher> listTeachers = Optional.ofNullable(teacherDao.allTeachersForAdmin())
        .orElseThrow(() -> new DataIsNotExistsException("Teachers data have not been registered "));

    List<TeacherForAdminDTO> listResult = new ArrayList<>();
    listTeachers.forEach(val -> {
      TeacherForAdminDTO teacherAdminDTO = new TeacherForAdminDTO();
      teacherAdminDTO.setId(val.getId());
      teacherAdminDTO.setCode(val.getCode());
      teacherAdminDTO.setPhone(val.getPhone());
      teacherAdminDTO.setGender(val.getGender());
      teacherAdminDTO.setUsername(val.getUser().getUsername());
      teacherAdminDTO.setVersion(val.getVersion());
      teacherAdminDTO.setFirstName(val.getUser().getFirstName());
      teacherAdminDTO.setLastName(val.getUser().getLastName());

      listResult.add(teacherAdminDTO);
    });

    return listResult;
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

    Role role = new Role();
    role.setId(data.getRoleId());
    role.setVersion(data.getRoleVersion());
    user.setRole(role);

    Teacher teacher = new Teacher();
    teacher.setUser(user);
    teacher.setCode(data.getCode());
    teacher.setPhone(data.getPhone());
    teacher.setGender(data.getGender());
    teacher.setTitleDegree(data.getTitleDegree());
    teacher.setCreatedAt(LocalDateTime.now());
    teacher.setCreatedBy(data.getCreatedBy());

    try {
      begin();
      userService.addUser(user);
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
    Teacher teacher = teacherDao.findTeacherByIdCustom(id);
    if (teacher == null) {
      throw new DataIsNotExistsException("Id", id);
    }

    List<ExperienceResponseDto> experiences = experienceService.getAllByTeacherId(id);

    TeacherProfileDTO teacherProfile = new TeacherProfileDTO();
    teacherProfile.setId(id);
    teacherProfile.setFirstName(teacher.getUser().getFirstName());
    teacherProfile.setLastName(teacher.getUser().getLastName());
    teacherProfile.setEmail(teacher.getUser().getEmail());
    teacherProfile.setCreatedAt(teacher.getCreatedAt());
    teacherProfile.setGender(teacher.getGender());
    teacherProfile.setPhotoId(null == teacher.getUser().getUserPhoto().getId() ? ""
        : teacher.getUser().getUserPhoto().getId());
    teacherProfile.setExperiences(experiences);
    return teacherProfile;
  }

  @Override
  public void updateTeacher(UpdateTeacherRequestDTO data) throws Exception {
    validUtil.validate(data);
    Teacher teacherDB = Optional.ofNullable(findTeacherById(data.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("id", data.getId()));

    teacherDB.setTitleDegree(data.getTitleDegree());
    teacherDB.setGender(data.getGender());
    teacherDB.setUpdatedBy(data.getUpdatedBy());
    teacherDB.setUpdatedAt(LocalDateTime.now());

    User user = new User();
    user.setFirstName(data.getFirstName());
    user.setId(teacherDB.getUser().getId());
    user.setLastName(data.getLastName());
    user.setEmail(data.getEmail());
    try {
      begin();
      userService.updateUser(user);
      teacherDB.setUser(user);
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
  public void deleteTeacherById(DeleteTeacherDTO deleteReq) throws Exception {
    validateNullId(deleteReq.getId(), "Id");

    try {
      begin();
      teacherDao.deleteTeacherById(deleteReq.getId());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (e.getMessage().equals("ID Not Found")) {
        throw new DataIsNotExistsException("Id");
      }
      begin();
      updateIsActive(deleteReq.getId(), deleteReq.getUpdatedBy());
      commit();
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

}
