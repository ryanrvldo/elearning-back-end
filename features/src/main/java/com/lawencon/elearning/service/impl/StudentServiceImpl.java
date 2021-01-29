package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.student.RegisterStudentDTO;
import com.lawencon.elearning.dto.student.StudentDashboardDTO;
import com.lawencon.elearning.dto.student.StudentProfileDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * 
 * @author WILLIAM
 *
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl implements StudentService {

  @Autowired
  private StudentDao studentDao;

  @Autowired
  private CourseService courseService;

  @Autowired
  private UserService userService;

  @Autowired
  private ValidationUtil validationUtil;

  @Override
  public void insertStudent(RegisterStudentDTO data) throws Exception {
    validationUtil.validate(data);
    Student student = new Student();
    student.setCode(data.getCode());
    User user = new User();
    user.setFirstName(data.getFirstName());
    user.setLastName(data.getLastName());
    user.setEmail(data.getEmail());
    user.setUsername(data.getUsername());
    user.setPassword(data.getPassword());
    user.setCreatedAt(LocalDateTime.now());
    user.setCreatedBy(data.getCreatedBy());
    Role role = new Role();
    role.setId(data.getRoleId());
    role.setVersion(data.getRoleVersion());
    user.setRole(role);
    student.setUser(user);
    student.setPhone(data.getPhone());
    student.setGender(Gender.valueOf(data.getGender()));
    student.setCreatedBy(data.getCreatedBy());
    try {
      begin();
      userService.addUser(user);
      studentDao.insertStudent(student, null);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

  @Override
  public Student getStudentById(String id) throws Exception {
    validateNullId(id, "id");
    return Optional.ofNullable(studentDao.getStudentById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public StudentProfileDTO getStudentProfile(String id) throws Exception {
    validateNullId(id, "id");
    Student std = Optional.ofNullable(studentDao.getStudentProfile(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
    StudentProfileDTO stdProfile = new StudentProfileDTO();
    stdProfile.setCreatedAt(std.getCreatedAt());
    stdProfile.setEmail(std.getUser().getEmail());
    stdProfile.setFirstName(std.getUser().getFirstName());
    stdProfile.setLastName(std.getUser().getLastName());
    stdProfile.setGender(std.getGender());
    return stdProfile;
  }

  @Override
  public void updateStudentProfile(Student data) throws Exception {
    validateNullId(data.getId(), "id");
    setupUpdatedValue(data, () -> studentDao.getStudentById(data.getId()));
    studentDao.updateStudentProfile(data, null);
  }

  @Override
  public void deleteById(String id) throws Exception {
    validateNullId(id, "id");
    begin();
    studentDao.deleteStudentById(id);
    commit();
  }

  @Override
  public void updateIsActive(Student data) throws Exception {
    data.setIsActive(false);
    studentDao.updateIsActive(data.getId(), data.getUser().getId());
  }

  @Override
  public Student getStudentByIdUser(String id) throws Exception {
    validateNullId(id, "user id");
    return Optional.ofNullable(studentDao.getStudentByIdUser(id))
        .orElseThrow(() -> new DataIsNotExistsException("user id", id));
  }

  @Override
  public StudentDashboardDTO getStudentDashboard(String id) throws Exception {
    validateNullId(id, "id");
    Student std = Optional.ofNullable(studentDao.getStudentDashboard(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
    StudentDashboardDTO stdDashboard = new StudentDashboardDTO();
    stdDashboard.setCreatedAt(std.getCreatedAt());
    stdDashboard.setEmail(std.getUser().getEmail());
    stdDashboard.setFirstName(std.getUser().getFirstName());
    stdDashboard.setGender(std.getGender());
    stdDashboard.setId(std.getId());
    stdDashboard.setIdPhoto(std.getUser().getUserPhoto().getId());
    stdDashboard.setLastName(std.getUser().getLastName());
    stdDashboard.setPhone(std.getPhone());
    return stdDashboard;
  }

  @Override
  public List<CourseResponseDTO> getStudentCourse(String id) throws Exception {
    List<CourseResponseDTO> listResult = courseService.getMyCourse(id);
    if (listResult.isEmpty()) {
      throw new DataIsNotExistsException("You haven't select any course");
    }
    return listResult;
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
