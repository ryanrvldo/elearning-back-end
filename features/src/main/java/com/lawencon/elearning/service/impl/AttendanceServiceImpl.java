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
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.AttendanceService;
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
    Student student = new Student();
    student.setId(attendanceDTO.getIdStudent());
    student.setVersion(attendanceDTO.getStudentVersion());
    Attendance data = new Attendance();
    data.setStudent(student);
    Module module = new Module();
    module.setId(attendanceDTO.getIdModule());
    module.setVersion(attendanceDTO.getModuleVersion());
    data.setModule(module);
    data.setCreatedBy(student.getId());
    data.setCreatedAt(LocalDateTime.now());
    data.setIsVerified(false);
    data.setTrxNumber(TransactionNumberUtils.generateAttendanceTrxNumber());
    data.setTrxDate(LocalDate.now());
    attendanceDao.createAttendance(data, null);
  }

  @Override
  public void verifyAttendance(String id, String userId) throws Exception {
    validateNullId(id, "id");
    validateNullId(userId, "user id");
    Attendance attendance = attendanceDao.getAttendanceById(id);
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
