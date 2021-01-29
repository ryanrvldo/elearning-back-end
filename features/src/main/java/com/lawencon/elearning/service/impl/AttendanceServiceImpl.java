package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.AttendanceService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.util.TransactionNumberUtils;

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

  @Override
  public List<Attendance> getAttendanceList(String idModule) throws Exception {
    List<Attendance> listResult = attendanceDao.getAttendanceList(idModule);
    if (listResult.isEmpty()) {
      throw new DataIsNotExistsException("There is no attendance yet");
    }
    return listResult;
  }

  @Override
  public void createAttendance(Attendance data) throws Exception {
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
    Attendance attendance = attendanceDao.getAttendanceById(id);
    attendance.setIsVerified(true);
    attendance.setUpdatedBy(userId);
    attendance.setUpdatedAt(LocalDateTime.now());
    attendanceDao.verifAttendance(attendance, null);
  }

}
