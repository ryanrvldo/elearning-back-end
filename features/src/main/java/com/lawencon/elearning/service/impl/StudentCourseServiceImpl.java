package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.StudentCourseDao;
import com.lawencon.elearning.dto.EmailSetupDTO;
import com.lawencon.elearning.dto.StudentCourseRegisterRequestDTO;
import com.lawencon.elearning.dto.StudentListByCourseResponseDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.GeneralCode;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.StudentCourse;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.EmailService;
import com.lawencon.elearning.service.GeneralService;
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
  private StudentCourseDao studentCourseDao;

  @Autowired
  private StudentService studentService;

  @Autowired
  private CourseService courseService;

  @Autowired
  private GeneralService generalService;

  @Autowired
  private EmailService emailService;

  @Override
  public void registerCourse(StudentCourseRegisterRequestDTO studentCourseRegister)
      throws Exception {
    validateUtil.validate(studentCourseRegister);
    validateUtil.validateUUID(studentCourseRegister.getCourseId(),
        studentCourseRegister.getStudentId());
    StudentCourse studentCourse = new StudentCourse();
    Student student = studentService.getStudentById(studentCourseRegister.getStudentId());
    Course course = courseService.getCourseById(studentCourseRegister.getCourseId());
    studentCourse.setCourse(course);
    studentCourse.setCreatedBy(student.getUser().getId());
    studentCourse.setStudent(student);
    studentCourseDao.registerCourse(studentCourse, null);
  }

  @Override
  public void verifyRegisterCourse(String studentCourseId, String userId, String email)
      throws Exception {
    validateUtil.validateUUID(studentCourseId, userId);
    StudentCourse studentCourse = studentCourseDao.getStudentCourseById(studentCourseId);
    if (studentCourse == null) {
      throw new DataIsNotExistsException("student course id" + studentCourseId);
    }
    studentCourse.setIsVerified(true);
    studentCourse.setVerifiedAt(LocalDateTime.now());
    studentCourse.setUpdatedBy(userId);
    studentCourseDao.verifyRegisterCourse(studentCourse, null);

    Course course = courseService.getCourseById(studentCourse.getCourse().getId());
    String template = generalService.getTemplateHTML(GeneralCode.REGISTER_COURSE_SUCCESS.getCode())
        .replace("?1", course.getCourseType().getName());
    EmailSetupDTO emailSetup = new EmailSetupDTO();
    emailSetup.setReceiver(email);
    emailSetup.setSubject("Course Registration Status");
    emailSetup.setHeading("Congratulation!");
    emailSetup.setBody(template);
    emailService.send(emailSetup);
  }

  @Override
  public List<StudentListByCourseResponseDTO> getListStudentCourseById(String courseId)
      throws Exception {
    validateUtil.validateUUID(courseId);
    Course course = courseService.getCourseById(courseId);
    if (course == null) {
      throw new DataIsNotExistsException("course id" + courseId);
    }
    return studentCourseDao.getListStudentCourseById(courseId);
  }

  @Override
  public StudentCourse getStudentCourseById(String id) throws Exception {
    validateUtil.validateUUID(id);
    StudentCourse studentCourse = studentCourseDao.getStudentCourseById(id);
    if (studentCourse == null) {
      throw new DataIsNotExistsException("student course id" + id);
    }
    return studentCourse;
  }

  @Override
  public Boolean checkVerifiedCourse(String studentId, String courseId) throws Exception {
    validateUtil.validateUUID(studentId);
    Student student = studentService.getStudentById(studentId);
    if (student == null) {
      throw new DataIsNotExistsException("student id" + studentId);
    }
    Boolean studentCourse = studentCourseDao.checkVerifiedCourse(studentId, courseId);
    if (studentCourse == null) {
      throw new DataIsNotExistsException("student id" + studentId);
    }
    return studentCourse;
  }

}
