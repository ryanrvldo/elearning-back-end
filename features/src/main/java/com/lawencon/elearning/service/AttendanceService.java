package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.model.Attendance;

/**
 * 
 * @author WILLIAM
 *
 */
public interface AttendanceService {

  List<Attendance> getAttendanceList(String idModule) throws Exception;

  void createAttendance(Attendance data) throws Exception;

  void verifAttendance(Attendance data) throws Exception;
}
