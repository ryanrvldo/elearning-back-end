package com.lawencon.elearning.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.ExamType;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

/**
 * @author WILLIAM
 */
@Repository
public class StudentDaoImpl extends CustomBaseDao<Student> implements StudentDao {

  @Override
  public void insertStudent(Student data, Callback before) throws Exception {
    save(data, before, null, false, false);
  }

  @Override
  public Student getStudentById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public Student getStudentProfile(String id) throws Exception {
    String query =
        buildQueryOf("SELECT u.first_name, u.last_name, s.gender, s.created_at, u.email ",
            "FROM tb_m_students s INNER JOIN tb_m_users u ON u.id = s.id_user ", "WHERE s.id = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
    List<Student> listResult = HibernateUtils.bMapperList(listObj, Student.class, "user.firstName",
        "user.lastName", "gender", "createdAt", "user.email");
    return getResultModel(listResult);
  }

  @Override
  public void updateStudentProfile(Student data, Callback before) throws Exception {
    save(data, before, null);
  }

  @Override
  public void deleteStudentById(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    String query = "UPDATE tb_m_students SET is_active = false";
    updateNativeSQL(query, id, userId);
  }

  @Override
  public Student getStudentByIdUser(String id) throws Exception {
    String query = buildQueryOf("SELECT s.id, u.first_name, u.last_name ",
        "FROM tb_m_students AS s ", "INNER JOIN tb_m_users as u", "WHERE id_user=?1 ");
    List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
    List<Student> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Student student = new Student();
      student.setId((String) objArr[0]);
      User user = new User();
      user.setFirstName((String) objArr[1]);
      user.setLastName((String) objArr[2]);
      student.setUser(user);
      listResult.add(student);
    });
    return getResultModel(listResult);
  }

  @Override
  public Student getStudentDashboard(String id) throws Exception {
    String query = buildQueryOf(
        "SELECT s.id as id_student, f.id as id_file, u.username, u.first_name, u.last_name, u.email, s.phone, s.gender, s.created_at ",
        "FROM tb_m_students s ", "INNER JOIN tb_m_users u ON u.id = s.id_user ",
        "LEFT JOIN tb_r_files f ON f.id = u.id_photo ", "WHERE s.id = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
    List<Student> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Student std = new Student();
      std.setId((String) objArr[0]);
      File file = new File();
      file.setId((String) objArr[1]);
      User user = new User();
      user.setUserPhoto(file);
      user.setUsername((String) objArr[2]);
      user.setFirstName((String) objArr[3]);
      user.setLastName((String) objArr[4]);
      user.setEmail((String) objArr[5]);
      std.setUser(user);
      std.setPhone((String) objArr[6]);
      std.setGender((Gender.valueOf((String) objArr[7])));
      Timestamp created = (Timestamp) objArr[8];
      std.setCreatedAt(created.toLocalDateTime());
      listResult.add(std);
    });
    return getResultModel(listResult);
  }

  @Override
  public List<Student> findAll() throws Exception {
    String query = buildQueryOf(
        "SELECT s.id, s.code, s.phone, s.gender, s.createdAt, s.isActive, u.username, u.firstName, u.lastName, u.email, f.id ",
        "FROM Student AS s ",
        "INNER JOIN User AS u ON u.id = s.user.id ",
        "LEFT JOIN File AS f ON f.id = u.userPhoto.id ");
    List<Student> studentList = new ArrayList<>();
    List<Object[]> objList = createQuery(query, Object[].class).getResultList();
    objList.forEach(objArr -> {
      Student student = new Student();
      student.setId((String) objArr[0]);
      student.setCode((String) objArr[1]);
      student.setPhone((String) objArr[2]);
      student.setGender((Gender) objArr[3]);
      student.setCreatedAt((LocalDateTime) objArr[4]);
      student.setIsActive((Boolean) objArr[5]);

      User user = new User();
      user.setUsername((String) objArr[6]);
      user.setFirstName((String) objArr[7]);
      user.setLastName((String) objArr[8]);
      user.setEmail((String) objArr[9]);

      File file = new File();
      file.setId((String) objArr[10]);
      user.setUserPhoto(file);
      student.setUser(user);

      studentList.add(student);
    });

    return studentList;
  }

  @Override
  public List<Student> getListStudentByIdCourse(String idCourse) throws Exception {
    String query =
        buildQueryOf("SELECT s.id, s.code, u.first_name, u.last_name, u.email, s.phone, s.gender ",
            "FROM student_course sc ", "INNER JOIN tb_m_students s ON s.id = sc.id_student ",
            "INNER JOIN tb_m_users u ON u.id = s.id_user ", "WHERE sc.id_course = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, idCourse).getResultList();
    List<Student> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Student student = new Student();
      student.setId((String) objArr[0]);
      student.setCode((String) objArr[1]);
      User user = new User();
      user.setFirstName((String) objArr[2]);
      user.setLastName((String) objArr[3]);
      user.setEmail((String) objArr[4]);
      student.setUser(user);
      student.setPhone((String) objArr[5]);
      student.setGender(Gender.valueOf((String) objArr[6]));
      listResult.add(student);
    });
    return listResult;
  }

  @Override
  public List<DetailExam> getStudentExamReport(String studentId) throws Exception {
    String sql = buildQueryOf(
        "SELECT ct.type_name AS course, sc.subject_name , e.\"exam_type\" AS exam_type , e.exam_title, e.trx_date , de.grade ",
        "FROM tb_r_dtl_exams AS de ",
        "INNER JOIN tb_r_exams AS e ON de.id_exam = e.id ",
        "INNER JOIN tb_m_modules AS m ON e.id_module = m.id ",
        "INNER JOIN tb_m_subject_categories AS sc ON m.id_subject = sc.id ",
        "INNER JOIN  tb_m_courses AS c ON m.id_course = c.id ",
        "INNER JOIN tb_m_course_types AS ct ON ct.id = c.id_course_type WHERE de.id_student =  ?1");
    List<?> listObj = createNativeQuery(sql).setParameter(1, studentId).getResultList();
    List<DetailExam> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      DetailExam detailExam = new DetailExam();
      Exam exam = new Exam();
      Module module = new Module();
      SubjectCategory subjectCategory = new SubjectCategory();
      Course course = new Course();
      CourseType courseType = new CourseType();
      courseType.setName((String) objArr[0]);
      course.setCourseType(courseType);
      subjectCategory.setSubjectName((String) objArr[1]);
      module.setSubject(subjectCategory);
      module.setCourse(course);
      exam.setModule(module);
      exam.setExamType(ExamType.valueOf((String) objArr[2]));
      exam.setTitle((String) objArr[3]);
      Date inDate = (Date) objArr[4];
      exam.setTrxDate(inDate.toLocalDate());
      detailExam.setGrade((Double) objArr[5]);
      detailExam.setExam(exam);
      listResult.add(detailExam);
    });
    return listResult;
  }

}
