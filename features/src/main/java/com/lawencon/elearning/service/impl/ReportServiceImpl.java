package com.lawencon.elearning.service.impl;

import com.lawencon.elearning.dto.AttendanceResponseDTO;
import com.lawencon.elearning.dto.course.DetailCourseResponseDTO;
import com.lawencon.elearning.dto.student.StudentReportDTO;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.dto.teacher.TeacherReportResponseDTO;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.service.AttendanceService;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.ReportService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.service.TeacherService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rian Rivaldo
 */
@Service
public class ReportServiceImpl implements ReportService {

  @Autowired
  private CourseService courseService;

  @Autowired
  private TeacherService teacherService;

  @Autowired
  private StudentService studentService;

  @Autowired
  private AttendanceService attendanceService;

  @Override
  public DetailCourseResponseDTO getDetailCourseReport(String courseId, String studentId)
      throws Exception {
    return courseService.getDetailCourse(courseId, studentId);
  }

  @Override
  public Course getCourseById(String id) throws Exception {
    return courseService.getCourseById(id);
  }

  @Override
  public List<CourseAttendanceReportByTeacher> getCourseAttendanceReport(String courseId)
      throws Exception {
    return courseService.getCourseAttendanceReport(courseId);
  }

  @Override
  public Teacher findTeacherById(String id) throws Exception {
    return teacherService.findTeacherById(id);
  }

  @Override
  public List<TeacherReportResponseDTO> getTeacherDetailCourseReport(String moduleId)
      throws Exception {
    return teacherService.getTeacherDetailCourseReport(moduleId);
  }

  @Override
  public Student getStudentById(String id) throws Exception {
    return studentService.getStudentById(id);
  }

  @Override
  public List<StudentReportDTO> getStudentExamReport(String studentId) throws Exception {
    return studentService.getStudentExamReport(studentId);
  }

  @Override
  public List<AttendanceResponseDTO> getAttendanceReport(String idCourse, String idModule)
      throws Exception {
    return attendanceService.getAttendanceList(idCourse, idModule);
  }

  @Override
  public Module getModuleForAttendanceReport(String idModule) throws Exception {
    return attendanceService.getModuleForAttendanceReport(idModule);
  }
}
