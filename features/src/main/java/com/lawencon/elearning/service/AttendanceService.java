package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.AttendanceRequestDTO;
import com.lawencon.elearning.dto.AttendanceResponseDTO;
import com.lawencon.elearning.dto.VerifyAttendanceRequestDTO;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.model.Module;

/**
 * 
 * @author WILLIAM
 *
 */
public interface AttendanceService {

  List<AttendanceResponseDTO> getAttendanceList(String idCourse, String idModule) throws Exception;

  void createAttendance(AttendanceRequestDTO data) throws Exception;

  void verifyAttendance(VerifyAttendanceRequestDTO data) throws Exception;

  String checkAttendanceStatus(String idModule, String idStudent) throws Exception;

  Module getModuleForAttendanceReport(String idModule) throws Exception;

  void getAttendanceForDetailCourse(String studentId, ModuleResponseDTO data) throws Exception;
}
