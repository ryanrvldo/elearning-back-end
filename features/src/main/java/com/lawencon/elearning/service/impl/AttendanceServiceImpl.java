package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.dto.AttendanceRequestDTO;
import com.lawencon.elearning.dto.AttendanceResponseDTO;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import com.lawencon.elearning.error.AttendanceErrorException;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Student;
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
    validateNullId(idModule, "module id");
    validateNullId(idCourse, "course id");
    Optional.ofNullable(moduleService.getModuleById(idModule))
        .orElseThrow(() -> new DataIsNotExistsException("module id", idModule));
    Optional.ofNullable(courseService.getCourseById(idCourse))
        .orElseThrow(() -> new DataIsNotExistsException("course id", idCourse));
    List<Attendance> listAttendance = attendanceDao.getAttendanceList(idModule);
    List<StudentByCourseResponseDTO> listStudent =
        studentService.getListStudentByIdCourse(idCourse);
    if (listStudent.isEmpty()) {
      throw new DataIsNotExistsException("No student register yet.");
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
  public void verifyAttendance(String id, String userId) throws Exception {
    validateNullId(id, "attendance id");
    validateNullId(userId, "user id");
    Attendance attendance = Optional.ofNullable(attendanceDao.getAttendanceById(id))
        .orElseThrow(() -> new DataIsNotExistsException("attendance id", id));
    Optional.ofNullable(userService.getById(userId))
        .orElseThrow(() -> new DataIsNotExistsException("user id", userId));
    attendance.setIsVerified(true);
    attendance.setUpdatedBy(userId);
    attendance.setUpdatedAt(LocalDateTime.now());
    attendanceDao.verifyAttendance(attendance, null);
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

  @Override
  public String checkAttendanceStatus(String idModule, String idStudent) throws Exception {
    validateNullId(idModule, "module id");
    validateNullId(idStudent, "student id");
    Optional.ofNullable(studentService.getStudentById(idStudent))
        .orElseThrow(() -> new DataIsNotExistsException("student id", idStudent));
    Optional.ofNullable(moduleService.getModuleById(idModule))
        .orElseThrow(() -> new DataIsNotExistsException("module id", idModule));
    Attendance attendance = attendanceDao.checkAttendanceStatus(idModule, idStudent);
    System.out.println(attendance);
    if (null == attendance) {
      return "0";
    } else {
      if (attendance.getIsVerified() == false) {
        return "1";
      } else if (attendance.getIsVerified() == true) {
        return "2";
      }
    }
    return "0";
  }

}
