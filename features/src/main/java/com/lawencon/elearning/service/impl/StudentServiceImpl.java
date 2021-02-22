package com.lawencon.elearning.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.admin.DashboardStudentResponseDto;
import com.lawencon.elearning.dto.admin.RegisteredStudentMonthlyDto;
import com.lawencon.elearning.dto.course.CourseResponseDTO;
import com.lawencon.elearning.dto.student.RegisterStudentDTO;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import com.lawencon.elearning.dto.student.StudentDashboardDTO;
import com.lawencon.elearning.dto.student.StudentReportDTO;
import com.lawencon.elearning.dto.student.StudentUpdateRequestDto;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.InternalServerErrorException;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.DetailExamService;
import com.lawencon.elearning.service.EmailService;
import com.lawencon.elearning.service.GeneralService;
import com.lawencon.elearning.service.RoleService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.service.UserTokenService;
import com.lawencon.elearning.util.TransactionNumberUtils;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author WILLIAM
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
  private RoleService roleService;

  @Autowired
  private DetailExamService detailExamService;

  @Autowired
  private UserTokenService userTokenService;

  @Autowired
  private EmailService emailService;

  @Autowired
  private GeneralService generalService;

  @Autowired
  private ValidationUtil validationUtil;

  @Override
  public void insertStudent(RegisterStudentDTO data) throws Exception {
    validationUtil.validate(data);
    Student student = new Student();
    student.setCode(TransactionNumberUtils.generateStudentCode());
    student.setPhone(data.getPhone());
    student.setGender(Gender.valueOf(data.getGender()));
    student.setIsActive(false);

    User user = new User();
    user.setFirstName(data.getFirstName());
    user.setLastName(data.getLastName());
    user.setEmail(data.getEmail());
    user.setUsername(data.getUsername());
    user.setPassword(data.getPassword());
    user.setIsActive(false);

    Role role = roleService.findByCode(Roles.STUDENT.getCode());
    user.setRole(role);
    student.setUser(user);

    try {
      begin();
      userService.addUser(user);
      studentDao.insertStudent(student, null);
      userTokenService.sendUserTokenToEmail(data.getEmail());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

  @Override
  public Student getStudentById(String id) throws Exception {
    validationUtil.validateUUID(id);
    return Optional.ofNullable(studentDao.getStudentById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public Student getStudentProfile(String id) throws Exception {
    validationUtil.validateUUID(id);
    return Optional.ofNullable(studentDao.getStudentProfile(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public void updateStudentProfile(StudentUpdateRequestDto request) throws Exception {
    validationUtil.validate(request);

    User userDb = Optional.ofNullable(userService.getById(request.getUpdatedBy()))
        .orElseThrow(() -> new DataIsNotExistsException("user id", request.getUpdatedBy()));

    if (!userDb.getRole().getCode().equalsIgnoreCase(Roles.ADMIN.getCode())
        && !userDb.getRole().getCode().equalsIgnoreCase(Roles.STUDENT.getCode())) {
      throw new IllegalAccessException("You are unauthorized");
    }

    Student prevStudent = Optional.ofNullable(studentDao.getStudentById(request.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("student id", request.getId()));
    if (userDb.getRole().getCode().equalsIgnoreCase(Roles.STUDENT.getCode())) {
      if (!userDb.getId().equalsIgnoreCase(prevStudent.getUser().getId())) {
        throw new IllegalAccessException("You Are Unauthorized");
      }
    }

    Student newStudent = new Student();
    newStudent.setCode(prevStudent.getCode());
    newStudent.setIsActive(prevStudent.getIsActive());
    newStudent.setUser(prevStudent.getUser());
    newStudent.setPhone(request.getPhone());
    newStudent.setGender(request.getGender());
    newStudent.setUpdatedBy(request.getUpdatedBy());

    User user = new User();
    user.setId(prevStudent.getUser().getId());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setUsername(request.getUsername());
    user.setUpdatedBy(request.getUpdatedBy());

    try {
      begin();
      userService.updateUser(user);
      setupUpdatedValue(newStudent, () -> prevStudent);
      studentDao.updateStudentProfile(newStudent, null);
      commit();
    } catch (Exception e) {
      rollback();
      throw new InternalServerErrorException(e.fillInStackTrace());
    }
  }

  @Override
  public void deleteStudent(String id) throws Exception {
    validationUtil.validateUUID(id);
    Student student = Optional.ofNullable(getStudentById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));

    try {
      begin();
      studentDao.deleteStudentById(student.getId());
      userService.deleteById(student.getUser().getId());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public void updateIsActive(UpdateIsActiveRequestDTO updateRequest) throws Exception {
    validationUtil.validate(updateRequest);
    Student student = getStudentById(updateRequest.getId());
    try {
      begin();
      studentDao.updateIsActive(student.getId(), updateRequest.getUpdatedBy(),
          updateRequest.getStatus());
      userService.updateActivateStatus(student.getUser().getId(), updateRequest.getStatus(),
          updateRequest.getUpdatedBy());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public Student getStudentByIdUser(String id) throws Exception {
    validationUtil.validateUUID(id);
    return Optional.ofNullable(studentDao.getStudentByIdUser(id))
        .orElseThrow(() -> new DataIsNotExistsException("user id", id));
  }

  @Override
  public StudentDashboardDTO getStudentDashboard(String id) throws Exception {
    validationUtil.validateUUID(id);
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
    validationUtil.validateUUID(id);
    Optional.ofNullable(studentDao.getStudentById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
    return courseService.getCourseByStudentId(id);
  }

  @Override
  public List<StudentByCourseResponseDTO> getListStudentByIdCourse(String idCourse)
      throws Exception {
    validationUtil.validateUUID(idCourse);
    List<Student> listStudent = studentDao.getListStudentByIdCourse(idCourse);
    if (listStudent.isEmpty()) {
      return Collections.emptyList();
    }

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

  @Override
  public List<Student> getAllStudentByCourseId(String idCourse) throws Exception {
    validationUtil.validateUUID(idCourse);
    return Optional.ofNullable(studentDao.getListStudentByIdCourse(idCourse))
        .orElse(Collections.emptyList());
  }

  @Override
  public List<StudentReportDTO> getStudentExamReport(String studentId) throws Exception {
    validationUtil.validateUUID(studentId);
    List<DetailExam> listDetail = detailExamService.getStudentExamReport(studentId);
    if (listDetail.isEmpty()) {
      return Collections.emptyList();
    }

    List<StudentReportDTO> listResult = new ArrayList<>();
    NumberFormat numberFormat = new DecimalFormat("#0.00");
    for (DetailExam detailExam : listDetail) {
      StudentReportDTO studentDTO = new StudentReportDTO();
      studentDTO.setCourseName(
          detailExam.getExam().getModule().getCourse().getCourseType().getName());
      studentDTO
          .setModuleName(detailExam.getExam().getModule().getSubject().getSubjectName());
      studentDTO.setExamType(detailExam.getExam().getExamType().toString());
      studentDTO.setExamTitle(detailExam.getExam().getTitle());
      studentDTO.setDateExam(detailExam.getExam().getTrxDate().toString());
      studentDTO.setGrade(Double.valueOf(numberFormat.format(detailExam.getGrade().doubleValue())));
      listResult.add(studentDTO);
    }

    return listResult;
  }

  @Override
  public List<StudentDashboardDTO> getAll() throws Exception {
    List<Student> studentList = studentDao.findAll();
    if (studentList.isEmpty()) {
      return Collections.emptyList();
    }
    return studentList.stream()
        .map(student -> new StudentDashboardDTO(
            student.getId(),
            student.getCode(),
            student.getUser().getUsername(),
            student.getUser().getFirstName(),
            student.getUser().getLastName(),
            student.getUser().getEmail(),
            student.getPhone(),
            student.getGender(),
            student.getUser().getUserPhoto().getId(),
            student.getCreatedAt(),
            student.getIsActive()))
        .collect(Collectors.toList());
  }

  @Override
  public DashboardStudentResponseDto getStudentDataForAdmin() throws Exception {
    DashboardStudentResponseDto studentDashboardData = studentDao.countStudentData();
    studentDashboardData.setRegisteredToCourse(courseService.getRegisterStudent());
    studentDashboardData.setVerified(0);
    return studentDashboardData;
  }

  @Override
  public Integer countTotalStudent() throws Exception {
    return studentDao.countTotalStudent();
  }

  @Override
  public List<RegisteredStudentMonthlyDto> countRegisteredStudentMonthly() throws Exception {
    return studentDao.countTotalRegisteredStudent();
  }

}
