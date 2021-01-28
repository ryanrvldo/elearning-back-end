package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
public interface AttendanceDao {

  List<Attendance> getAttendanceList(String idModule) throws Exception;

  Attendance getAttendanceById(String id) throws Exception;

  void createAttendance(Attendance data, Callback before) throws Exception;

  void verifAttendance(Attendance data, Callback before) throws Exception;

}
