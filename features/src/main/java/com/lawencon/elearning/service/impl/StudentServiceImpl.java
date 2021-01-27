package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.dto.CourseResponseDTO;
import com.lawencon.elearning.dto.StudentDashboardDTO;
import com.lawencon.elearning.dto.StudentProfileDTO;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.StudentService;

/**
 * 
 * @author WILLIAM
 *
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl implements StudentService {

  @Autowired
  private StudentDao studentDao;

  @Autowired
  private CourseService courseService;

  @Override
  public void insertStudent(Student data) throws Exception {
    data.setCreatedAt(LocalDateTime.now());
    studentDao.insertStudent(data, null);
  }

  @Override
  public Student getStudentById(String id) throws Exception {
    return studentDao.getStudentById(id);
  }

  @Override
  public StudentProfileDTO getStudentProfile(String id) throws Exception {
    Student std = studentDao.getStudentProfile(id);
    StudentProfileDTO stdProfile = new StudentProfileDTO();
    stdProfile.setCreatedAt(std.getCreatedAt());
    stdProfile.setEmail(std.getUser().getEmail());
    stdProfile.setFirstName(std.getUser().getFirstName());
    stdProfile.setLastName(std.getUser().getLastName());
    stdProfile.setGender(std.getGender());
    return stdProfile;
  }

  @Override
  public void updateStudentProfile(Student data) throws Exception {
    setupUpdatedValue(data, () -> studentDao.getStudentById(data.getId()));
    studentDao.updateStudentProfile(data, null);
  }

  @Override
  public void deleteById(String id) throws Exception {
    studentDao.deleteStudentById(id);
  }

  @Override
  public void updateIsActive(Student data) throws Exception {
    data.setIsActive(false);
    studentDao.updateIsActive(data.getId(), data.getUser().getId());
  }

  @Override
  public Student getStudentByIdUser(String id) throws Exception {
    return studentDao.getStudentByIdUser(id);
  }

  @Override
  public StudentDashboardDTO getStudentDashboard(String id) {
    Student std = studentDao.getStudentDashboard(id);
    StudentDashboardDTO stdDashboard = new StudentDashboardDTO();
    stdDashboard.setCreatedAt(std.getCreatedAt());
    stdDashboard.setEmail(std.getUser().getEmail());
    stdDashboard.setFirstName(std.getUser().getFirstName());
    stdDashboard.setGender(std.getGender());
    stdDashboard.setId(std.getId());
    stdDashboard.setIdPhoto(std.getUser().getUserPhoto().getId());
    stdDashboard.setLastName(std.getUser().getLastName());
    stdDashboard.setPhone(std.getPhone());
    return stdDashboard;
  }

  @Override
  public List<CourseResponseDTO> getStudentCourse(String id) throws Exception {
    return courseService.getMyCourse(id);
  }

}
