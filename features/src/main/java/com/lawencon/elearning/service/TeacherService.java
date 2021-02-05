package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.DeleteMasterRequestDTO;
import com.lawencon.elearning.dto.teacher.DashboardTeacherDTO;
import com.lawencon.elearning.dto.teacher.TeacherForAdminDTO;
import com.lawencon.elearning.dto.teacher.TeacherProfileDTO;
import com.lawencon.elearning.dto.teacher.TeacherRequestDTO;
import com.lawencon.elearning.dto.teacher.UpdateTeacherRequestDTO;
import com.lawencon.elearning.model.Teacher;

/**
 *  @author Dzaky Fadhilla Guci
 */

public interface TeacherService {

  List<Teacher> getAllTeachers() throws Exception;

  List<TeacherForAdminDTO> allTeachersForAdmin() throws Exception;

  void saveTeacher(TeacherRequestDTO data) throws Exception;

  Teacher findTeacherById(String id) throws Exception;

  TeacherProfileDTO findTeacherByIdCustom(String id) throws Exception;

  void setIsActiveTrue(DeleteMasterRequestDTO deleteReq) throws Exception;

  void setIsActiveFalse(DeleteMasterRequestDTO deleteReq) throws Exception;

  void updateTeacher(UpdateTeacherRequestDTO data) throws Exception;

  Teacher getFullNameByUserId(String userId) throws Exception;

  void deleteTeacherById(String teacherId) throws Exception;

  Teacher findByIdForCourse(String id) throws Exception;

  List<DashboardTeacherDTO> getTeacherDashboard(String id) throws Exception;
}
