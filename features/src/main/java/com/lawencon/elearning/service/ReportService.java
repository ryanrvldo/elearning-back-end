package com.lawencon.elearning.service;

import com.lawencon.elearning.dto.AttendanceResponseDTO;
import com.lawencon.elearning.dto.course.DetailCourseResponseDTO;
import com.lawencon.elearning.dto.student.StudentReportDTO;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.dto.teacher.TeacherReportResponseDTO;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.Teacher;
import java.util.List;

/**
 * @author Rian Rivaldo
 */
public interface ReportService {

  DetailCourseResponseDTO getDetailCourseReport(String courseId, String studentId) throws Exception;

  Course getCourseById(String id) throws Exception;

  Teacher getTeacherById(String teacherId) throws Exception;

  List<CourseAttendanceReportByTeacher> getCourseAttendanceReport(String courseId) throws Exception;

  Teacher findTeacherById(String id) throws Exception;

  List<TeacherReportResponseDTO> getTeacherDetailCourseReport(String moduleId) throws Exception;

  Student getStudentById(String id) throws Exception;

  List<StudentReportDTO> getStudentExamReport(String studentId) throws Exception;

  List<AttendanceResponseDTO> getAttendanceReport(String idCourse, String idModule) throws Exception;

  Module getModuleForAttendanceReport(String idModule) throws Exception;

}
