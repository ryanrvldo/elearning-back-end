package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.Attendance;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
public interface AttendanceDao {

  void createAttendance(Attendance data, Callback before) throws Exception;

}
