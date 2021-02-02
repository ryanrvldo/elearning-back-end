package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.dto.AttendanceRequestDTO;
import com.lawencon.elearning.dto.AttendanceResponseDTO;
import com.lawencon.elearning.error.AttendanceErrorException;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.AttendanceService;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.ModuleService;
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

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public List<AttendanceResponseDTO> getAttendanceList(String idCourse, String idModule)
      throws Exception {
    validateNullId(idModule, "module id");
    validateNullId(idCourse, "course id");
    Optional.ofNullable(moduleService.getModuleById(idModule))
        .orElseThrow(() -> new DataIsNotExistsException("module id", idModule));
    Optional.ofNullable(courseService.getCourseById(idCourse))
        .orElseThrow(() -> new DataIsNotExistsException("course id", idCourse));
    List<Attendance> listResult = attendanceDao.getAttendanceList(idCourse, idModule);
    List<AttendanceResponseDTO> listDTO = new ArrayList<>();
    for (Attendance attendance : listResult) {
      AttendanceResponseDTO attendanceDTO = new AttendanceResponseDTO();
      attendanceDTO.setAttendanceId(attendance.getId());
      attendanceDTO.setFirstName(attendance.getStudent().getUser().getFirstName());
      attendanceDTO.setLastName(attendance.getStudent().getUser().getLastName());
      attendanceDTO.setAttendanceTime(attendance.getCreatedAt());
      attendanceDTO.setAttendanceIsVerified(attendance.getIsVerified());
      attendanceDTO.setAttendanceVersion(attendance.getVersion());
      listDTO.add(attendanceDTO);
    }
    logger.info(String.valueOf(listDTO.size()));
    return listDTO;
  }

  @Override
  public void createAttendance(AttendanceRequestDTO attendanceDTO) throws Exception {
    validationUtil.validate(attendanceDTO);
    Module moduleDb = moduleService.getModuleByIdCustom(attendanceDTO.getIdModule());
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

      Student student = new Student();
      student.setId(attendanceDTO.getIdStudent());
      student.setVersion(attendanceDTO.getStudentVersion());
      data.setStudent(student);
      data.setCreatedBy(student.getId());

      Module module = new Module();
      module.setId(attendanceDTO.getIdModule());
      module.setVersion(attendanceDTO.getModuleVersion());
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

}
