package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.StudentCourseDao;
import com.lawencon.elearning.dto.StudentCourseRegisterRequestDTO;
import com.lawencon.elearning.dto.StudentListByCourseResponseDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.StudentCourse;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.StudentCourseService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author : Galih Dika Permana
 */
@Service
public class StudentCourseServiceImpl extends BaseServiceImpl implements StudentCourseService {

  @Autowired
  private ValidationUtil validateUtil;

  @Autowired
  private StudentCourseDao studentCoursDao;

  @Autowired
  private StudentService studentService;

  @Autowired
  private CourseService courseService;

  @Override
  public void registerCourse(StudentCourseRegisterRequestDTO studentCourseRegister)
      throws Exception {
    validateUtil.validate(studentCourseRegister);
    validateUtil.validateUUID(studentCourseRegister.getCourseId(),
        studentCourseRegister.getStudentId());
    StudentCourse studentCourse = new StudentCourse();
    Student student = studentService.getStudentById(studentCourseRegister.getStudentId());
    Course course = courseService.getCourseById(studentCourseRegister.getCourseId());
    studentCourse.setCourseId(course);
    studentCourse.setCreatedBy(student.getUser().getId());
    studentCourse.setStudentId(student);
    studentCoursDao.registerCourse(studentCourse, null);
  }

  @Override
  public void verifyRegisterCourse(String studentCourseId, String userId)
      throws Exception {
    validateUtil.validateUUID(studentCourseId, userId);
    StudentCourse studentCourse = studentCoursDao.getStudentCourseById(studentCourseId);
    if (studentCourse == null) {
      throw new DataIsNotExistsException("student course id" + studentCourseId);
    }
    studentCourse.setIsVerified(true);
    studentCourse.setVerifiedAt(LocalDateTime.now());
    studentCourse.setUpdatedBy(userId);
    studentCoursDao.verifyRegisterCourse(studentCourse, null);
  }

  @Override
  public List<StudentListByCourseResponseDTO> getListStudentCourseById(String courseId)
      throws Exception {
    validateUtil.validateUUID(courseId);
    Course course = courseService.getCourseById(courseId);
    if (course == null) {
      throw new DataIsNotExistsException("course id" + courseId);
    }
    List<StudentListByCourseResponseDTO> listResult =
        studentCoursDao.getListStudentCourseById(courseId);
    if (listResult.isEmpty()) {
      throw new DataIsNotExistsException("course id" + courseId);
    }
    return listResult;
  }

  @Override
  public StudentCourse getStudentCourseById(String id) throws Exception {
    validateUtil.validateUUID(id);
    StudentCourse studentCourse = studentCoursDao.getStudentCourseById(id);
    if (studentCourse == null) {
      throw new DataIsNotExistsException("student course id" + id);
    }
    return studentCourse;
  }

}
