package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.student.RegisterStudentDTO;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import com.lawencon.elearning.dto.student.StudentDashboardDTO;
import com.lawencon.elearning.dto.student.StudentProfileDTO;
import com.lawencon.elearning.dto.student.StudentReportDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.DetailExam;
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
    student.setPhone(data.getPhone());
    student.setGender(Gender.valueOf(data.getGender()));
    student.setCreatedBy(data.getCreatedBy());

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
    setupUpdatedValue(data, () -> Optional.ofNullable(studentDao.getStudentById(data.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("id", data.getId())));
    studentDao.updateStudentProfile(data, null);
  }

  @Override
  public void deleteStudent(DeleteMasterRequestDTO data) throws Exception {
    validationUtil.validate(data);
    try {
      begin();
      studentDao.deleteStudentById(data.getId());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (e.getMessage().equals("ID Not Found")) {
        throw new DataIsNotExistsException(e.getMessage());
      }
      updateIsActive(data.getId(), data.getUpdatedBy());
    }
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    begin();
    studentDao.updateIsActive(id, userId);
    commit();
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
    stdDashboard.setUsername(std.getUser().getUsername());
    return stdDashboard;
  }

  @Override
  public List<CourseResponseDTO> getStudentCourse(String id) throws Exception {
    validateNullId(id, "id");
    Optional.ofNullable(studentDao.getStudentById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
    List<CourseResponseDTO> listResult = courseService.getCourseByStudentId(id);
    if (listResult.isEmpty()) {
      throw new DataIsNotExistsException("You haven't select any course");
    }
    return listResult;
  }

  @Override
  public List<StudentByCourseResponseDTO> getListStudentByIdCourse(String idCourse)
      throws Exception {
    List<Student> listStudent = studentDao.getListStudentByIdCourse(idCourse);
    List<StudentByCourseResponseDTO> listDto = new ArrayList<>();
    for (Student element : listStudent) {
      StudentByCourseResponseDTO studentDto = new StudentByCourseResponseDTO();
      studentDto.setId(element.getId());
      studentDto.setCode(element.getCode());
      studentDto.setEmail(element.getUser().getEmail());
      studentDto.setFirstName(element.getUser().getFirstName());
      studentDto.setLastName((element.getUser().getLastName()));
      studentDto.setGender(String.valueOf(element.getGender()));
      studentDto.setPhone(element.getPhone());
      listDto.add(studentDto);
    }
    return listDto;
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

  public void RegisterCourse(String student, String course) throws Exception {
    courseService.registerCourse(student, course);
  }

  @Override
  public List<Student> getAllStudentByCourseId(String idCourse) throws Exception {
    return Optional.ofNullable(studentDao.getListStudentByIdCourse(idCourse))
        .orElseThrow(() -> new DataIsNotExistsException("course id", idCourse));
  }

  @Override
  public List<StudentReportDTO> getStudentExamReport(String studentId) throws Exception {
    validateNullId(studentId, "id");
    List<DetailExam> listDetail = studentDao.getStudentExamReport(studentId);
    List<StudentReportDTO> listResult = new ArrayList<>();
    if (listDetail.isEmpty()) {
      throw new DataIsNotExistsException("id", studentId);
    }
    for (int i = 0; i < listDetail.size(); i++) {
      StudentReportDTO studentDTO = new StudentReportDTO();
      studentDTO.setCourseName(
          listDetail.get(i).getExam().getModule().getCourse().getCourseType().getName());
      studentDTO
          .setModuleName(listDetail.get(i).getExam().getModule().getSubject().getSubjectName());
      studentDTO.setExamType(listDetail.get(i).getExam().getExamType().toString());
      studentDTO.setExamTitle(listDetail.get(i).getExam().getTitle());
      studentDTO.setDateExam(listDetail.get(i).getExam().getTrxDate().toString());
      studentDTO.setGrade(listDetail.get(i).getGrade());
      listResult.add(studentDTO);
    }

    return listResult;
  }

  @Override
  public List<StudentDashboardDTO> getAll() throws Exception {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    logger.info("Get All Student");
    List<Student> studentList = studentDao.findAll();
    logger.info("student list: " + studentList.size());
    if (studentList.isEmpty()) {
      throw new DataIsNotExistsException("There is no student yet.");
    }
    List<StudentDashboardDTO> responseList = studentList.stream()
        .map(student -> new StudentDashboardDTO(
            student.getId(),
            student.getUser().getUsername(),
            student.getUser().getFirstName(),
            student.getUser().getLastName(),
            student.getUser().getEmail(),
            student.getPhone(),
            student.getGender(),
            student.getUser().getUserPhoto().getId(),
            student.getCreatedAt()))
        .collect(Collectors.toList());
    logger.info("response list: " + responseList.size());
    return responseList;
  }

}
