package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.dto.admin.DashboardStudentResponseDto;
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
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.RoleService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.TransactionNumberUtils;
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

  @Autowired
  private RoleService roleService;

  @Override
  public void insertStudent(RegisterStudentDTO data) throws Exception {
    validationUtil.validate(data);
    Student student = new Student();
    student.setCode(TransactionNumberUtils.generateStudentCode());
    student.setPhone(data.getPhone());
    student.setGender(Gender.valueOf(data.getGender()));

    User user = new User();
    user.setFirstName(data.getFirstName());
    user.setLastName(data.getLastName());
    user.setEmail(data.getEmail());
    user.setUsername(data.getUsername());
    user.setPassword(data.getPassword());
    user.setCreatedAt(LocalDateTime.now());

    Role role = roleService.findByCode("RL-004");
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
    validationUtil.validateUUID(id);
    return Optional.ofNullable(studentDao.getStudentById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public void updateStudentProfile(StudentUpdateRequestDto request) throws Exception {
    validationUtil.validate(request);

    try {
      begin();
      Student prevStudent = Optional.ofNullable(studentDao.getStudentById(request.getId()))
          .orElseThrow(() -> new DataIsNotExistsException("student id", request.getId()));
      Student newStudent = new Student();
      newStudent.setCode(prevStudent.getCode());
      newStudent.setCourses(prevStudent.getCourses());
      newStudent.setIsActive(prevStudent.getIsActive());
      newStudent.setUser(prevStudent.getUser());
      newStudent.setPhone(request.getPhone());
      newStudent.setGender(request.getGender());

      User user = new User();
      user.setId(prevStudent.getUser().getId());
      user.setFirstName(request.getFirstName());
      user.setLastName(request.getLastName());
      user.setUsername(request.getUsername());
      userService.updateUser(user);
      setupUpdatedValue(newStudent, () -> prevStudent);
      studentDao.updateStudentProfile(newStudent, null);
      commit();
    } catch (Exception e) {
      rollback();
      throw new InternalServerErrorException(e.getCause());
    }
  }

  @Override
  public void deleteStudent(String studentId, String updatedBy) throws Exception {
    validationUtil.validateUUID(studentId, updatedBy);
    try {
      begin();
      Student student = getStudentById(studentId);
      userService.deleteById(student.getUser().getId());
      studentDao.deleteStudentById(student.getId());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (e.getMessage().equals("ID Not Found")) {
        throw new DataIsNotExistsException(e.getMessage());
      }
      updateIsActive(studentId, updatedBy);
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
    List<CourseResponseDTO> listResult = courseService.getCourseByStudentId(id);
    if (listResult.isEmpty()) {
      throw new DataIsNotExistsException("You haven't select any course");
    }
    return listResult;
  }

  @Override
  public List<StudentByCourseResponseDTO> getListStudentByIdCourse(String idCourse)
      throws Exception {
    validationUtil.validateUUID(idCourse);
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

  public void RegisterCourse(String student, String course) throws Exception {
    courseService.registerCourse(student, course);
  }

  @Override
  public List<Student> getAllStudentByCourseId(String idCourse) throws Exception {
    validationUtil.validateUUID(idCourse);
    return Optional.ofNullable(studentDao.getListStudentByIdCourse(idCourse))
        .orElseThrow(() -> new DataIsNotExistsException("course id", idCourse));
  }

  @Override
  public List<StudentReportDTO> getStudentExamReport(String studentId) throws Exception {
    validationUtil.validateUUID(studentId);
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
    List<Student> studentList = studentDao.findAll();
    if (studentList.isEmpty()) {
      throw new DataIsNotExistsException("There is no student yet.");
    }
    List<StudentDashboardDTO> responseList = studentList.stream()
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
    return responseList;
  }

  @Override
  public DashboardStudentResponseDto getStudentDataForAdmin() throws Exception {
    DashboardStudentResponseDto studentDashboardData = new DashboardStudentResponseDto();
    Integer totalActiveStudent = studentDao.countTotalStudentIsActiveTrue();
    Integer totalMaleStudent = studentDao.countTotalMaleStudent();
    Integer totalStudent = studentDao.countTotalStudent();
    studentDashboardData.setActive(totalActiveStudent);
    studentDashboardData.setInactive((totalStudent - totalActiveStudent));
    studentDashboardData.setMale(totalMaleStudent);
    studentDashboardData.setFemale((totalStudent - totalMaleStudent));
    studentDashboardData.setTotal(totalStudent);
    studentDashboardData.setVerified(0);
    return studentDashboardData;
  }

}
