package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.AttendanceRequestDTO;
import com.lawencon.elearning.model.Attendance;

/**
 * 
 * @author WILLIAM
 *
 */
public interface AttendanceService {

  List<Attendance> getAttendanceList(String idModule) throws Exception;

  void createAttendance(AttendanceRequestDTO data) throws Exception;

  void verifyAttendance(String id, String userId) throws Exception;
}
