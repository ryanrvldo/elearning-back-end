package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.AttendanceRequestDTO;
import com.lawencon.elearning.dto.AttendanceResponseDTO;

/**
 * 
 * @author WILLIAM
 *
 */
public interface AttendanceService {

  List<AttendanceResponseDTO> getAttendanceList(String idCourse, String idModule) throws Exception;

  void createAttendance(AttendanceRequestDTO data) throws Exception;

  void verifyAttendance(String id, String userId) throws Exception;

  String checkAttendanceStatus(String idModule, String idStudent) throws Exception;
}
