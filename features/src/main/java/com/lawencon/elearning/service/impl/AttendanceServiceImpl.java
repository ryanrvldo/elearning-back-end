package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.dto.AttendanceRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.AttendanceService;
import com.lawencon.elearning.service.StudentService;
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
  private StudentService studentService;

  @Autowired
  private ValidationUtil validationUtil;

  @Override
  public List<Attendance> getAttendanceList(String idModule) throws Exception {
    validateNullId(idModule, "module id");
    List<Attendance> listResult = attendanceDao.getAttendanceList(idModule);
    if (listResult.isEmpty()) {
      throw new DataIsNotExistsException("There is no attendance yet");
    }
    return listResult;
  }

  @Override
  public void createAttendance(AttendanceRequestDTO attendanceDTO) throws Exception {
    validationUtil.validate(attendanceDTO);
    Attendance data = new Attendance();
    data.getStudent().setId(attendanceDTO.getIdStudent());
    data.getStudent().setVersion(attendanceDTO.getStudentVersion());
    data.getModule().setId(attendanceDTO.getIdModule());
    data.getModule().setVersion(attendanceDTO.getModuleVersion());
    Student student = studentService.getStudentById(data.getStudent().getId());
    data.setCreatedBy(student.getUser().getId());
    data.setCreatedAt(LocalDateTime.now());
    data.setIsVerified(false);
    data.setTrxNumber(TransactionNumberUtils.generateAttendanceTrxNumber());
    data.setTrxDate(LocalDate.now());
    attendanceDao.createAttendance(data, null);
  }

  @Override
  public void verifAttendance(String id, String userId) throws Exception {
    validateNullId(id, "id");
    validateNullId(userId, "user id");
    Attendance attendance = attendanceDao.getAttendanceById(id);
    attendance.setIsVerified(true);
    attendance.setUpdatedBy(userId);
    attendance.setUpdatedAt(LocalDateTime.now());
    attendanceDao.verifAttendance(attendance, null);
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
