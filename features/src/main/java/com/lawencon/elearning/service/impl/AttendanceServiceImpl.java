package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.dto.AttendanceRequestDTO;
import com.lawencon.elearning.dto.AttendanceResponseDTO;
import com.lawencon.elearning.dto.VerifyAttendanceRequestDTO;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import com.lawencon.elearning.error.AttendanceErrorException;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.AttendanceService;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.ModuleService;
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
public class AttendanceServiceImpl extends BaseServiceImpl implements AttendanceService {

  @Autowired
  private AttendanceDao attendanceDao;

  @Autowired
  private ValidationUtil validationUtil;

  @Autowired
  private ModuleService moduleService;

  @Autowired
  private CourseService courseService;

  @Autowired
  private UserService userService;

  @Autowired
  private StudentService studentService;

  @Override
  public List<AttendanceResponseDTO> getAttendanceList(String idCourse, String idModule)
      throws Exception {
    validationUtil.validateUUID(idModule, idCourse);
    Optional.ofNullable(moduleService.getModuleById(idModule))
        .orElseThrow(() -> new DataIsNotExistsException("module id", idModule));
    Optional.ofNullable(courseService.getCourseById(idCourse))
        .orElseThrow(() -> new DataIsNotExistsException("course id", idCourse));
    List<Attendance> listAttendance = attendanceDao.getAttendanceList(idModule);
    List<StudentByCourseResponseDTO> listStudent =
        studentService.getListStudentByIdCourse(idCourse);
    if (listStudent.isEmpty()) {
      return Collections.emptyList();
    }
    List<AttendanceResponseDTO> listDTO = new ArrayList<>();
    for (StudentByCourseResponseDTO studentDTO : listStudent) {
      AttendanceResponseDTO attendanceDTO = new AttendanceResponseDTO();
      attendanceDTO.setId(studentDTO.getId());
      attendanceDTO.setCode(studentDTO.getCode());
      attendanceDTO.setEmail(studentDTO.getEmail());
      attendanceDTO.setFirstName(studentDTO.getFirstName());
      attendanceDTO.setLastName(studentDTO.getLastName());
      attendanceDTO.setGender(studentDTO.getGender());
      attendanceDTO.setPhone(studentDTO.getPhone());

      listAttendance.forEach(val -> {
        if (val.getStudent().getId().equals(studentDTO.getId())) {
          attendanceDTO.setAttendanceId(val.getId());
          attendanceDTO.setAttendanceIsVerified(val.getIsVerified());
          attendanceDTO.setAttendanceTime(val.getCreatedAt());
          attendanceDTO.setAttendanceVersion(val.getVersion());
        }
      });
      listDTO.add(attendanceDTO);
    }
    return listDTO;
  }

  @Override
  public void createAttendance(AttendanceRequestDTO attendanceDTO) throws Exception {
    validationUtil.validate(attendanceDTO);
    Module moduleDb = moduleService.getModuleById(attendanceDTO.getIdModule());
    if (LocalTime.now().isAfter(moduleDb.getSchedule().getEndTime())
        && LocalDate.now().isAfter(moduleDb.getSchedule().getDate())) {
      throw new AttendanceErrorException("You already late");
    } else if (LocalTime.now().isBefore(moduleDb.getSchedule().getEndTime())
        && LocalTime.now().isAfter(moduleDb.getSchedule().getStartTime())
        && LocalDate.now().isEqual(moduleDb.getSchedule().getDate())) {
      Attendance data = new Attendance();
      data.setCreatedAt(LocalDateTime.now());
      data.setIsVerified(false);
      data.setTrxNumber(TransactionNumberUtils.generateAttendanceTrxNumber());
      data.setTrxDate(LocalDate.now());

      Student studentDb = studentService.getStudentById(attendanceDTO.getIdStudent());
      Student student = new Student();
      student.setId(attendanceDTO.getIdStudent());
      student.setVersion(studentDb.getVersion());
      data.setStudent(student);
      data.setCreatedBy(student.getId());

      Module module = new Module();
      module.setId(attendanceDTO.getIdModule());
      module.setVersion(moduleDb.getVersion());
      data.setModule(module);
      attendanceDao.createAttendance(data, null);
    } else {
      throw new AttendanceErrorException("You can't absent");
    }
  }

  @Override
  public void verifyAttendance(VerifyAttendanceRequestDTO data) throws Exception {
    validationUtil.validate(data);
    if (data.getIdAttendance().isEmpty()) {
      throw new DataIsNotExistsException("No absent data");
    }
    User userDb = Optional.ofNullable(userService.getById(data.getUserId()))
        .orElseThrow(() -> new DataIsNotExistsException("user id", data.getUserId()));
    if (!userDb.getRole().getCode().equalsIgnoreCase(Roles.TEACHER.getCode())) {
      throw new IllegalAccessException("You are unauthorized");
    }
    Module moduleDb = moduleService.getModuleById(data.getModuleId());
    if (LocalTime.now().isAfter(moduleDb.getSchedule().getEndTime())
        && LocalDate.now().isAfter(moduleDb.getSchedule().getDate())) {
      throw new AttendanceErrorException("You already late");
    }
    for (String idAttendance : data.getIdAttendance()) {
      Attendance attendance = Optional.ofNullable(attendanceDao.getAttendanceById(idAttendance))
          .orElseThrow(() -> new DataIsNotExistsException("attendance id", idAttendance));
      if (attendance.getIsVerified()) {
        throw new AttendanceErrorException(
            "Attendance with id " + idAttendance + " already verified");
      }
      attendance.setIsVerified(true);
      attendance.setUpdatedBy(data.getUserId());
      attendance.setUpdatedAt(LocalDateTime.now());
      attendanceDao.verifyAttendance(attendance, null);
    }
  }

  @Override
  public String checkAttendanceStatus(String idModule, String idStudent) throws Exception {
    validationUtil.validateUUID(idModule, idStudent);
    Optional.ofNullable(studentService.getStudentById(idStudent))
        .orElseThrow(() -> new DataIsNotExistsException("student id", idStudent));
    Optional.ofNullable(moduleService.getModuleById(idModule))
        .orElseThrow(() -> new DataIsNotExistsException("module id", idModule));
    Attendance attendance = attendanceDao.checkAttendanceStatus(idModule, idStudent);
    System.out.println(attendance);
    if (null == attendance) {
      return "0";
    } else {
      if (!attendance.getIsVerified()) {
        return "1";
      } else if (attendance.getIsVerified()) {
        return "2";
      }
    }
    return "0";
  }

  @Override
  public Module getModuleForAttendanceReport(String idModule) throws Exception {
    return moduleService.getModuleByIdCustom(idModule);
  }

  @Override
  public void getAttendanceForDetailCourse(String studentId, ModuleResponseDTO data)
      throws Exception {
    attendanceDao.getAttendanceForDetailCourse(studentId, data);
  }

}
