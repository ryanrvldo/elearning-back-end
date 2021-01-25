package com.lawencon.elearning.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.service.AttendanceService;

/**
 * 
 * @author WILLIAM
 *
 */
@Service
public class AttendanceServiceImpl extends BaseServiceImpl implements AttendanceService {

  @Autowired
  private AttendanceDao attendanceDao;

  @Override
  public List<Attendance> getAttendanceList(String idModule) throws Exception {
    return attendanceDao.getAttendanceList(idModule);
  }

  @Override
  public void createAttendance(Attendance data) throws Exception {
    attendanceDao.createAttendance(data, null);
  }

  @Override
  public void verifAttendance(Attendance data) throws Exception {
    data.setIsVerified(true);
    attendanceDao.verifAttendance(data, null);
  }

}
