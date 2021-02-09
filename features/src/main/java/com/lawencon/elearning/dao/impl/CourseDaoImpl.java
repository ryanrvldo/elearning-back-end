package com.lawencon.elearning.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dto.course.CheckCourseRegisterRequestDTO;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.CourseStatus;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.Experience;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class CourseDaoImpl extends CustomBaseDao<Course> implements CourseDao {

  @Override
  public List<Course> getListCourse() throws Exception {
    return getAll();
  }

  @Override
  public void insertCourse(Course course, Callback before) throws Exception {
    save(course, before, null, true, true);
  }

  @Override
  public void updateCourse(Course course, Callback before) throws Exception {
    save(course, before, null, true, true);
  }

  @Override
  public void deleteCourse(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public List<Course> getCurrentAvailableCourse() throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id AS course_id,c.code AS course_code, ct.type_name AS typeName, c.capacity ,c.period_start ,c.period_end , ",
        "t.id AS teacher_id ,t.code AS teacher_code,e.title ,u.first_name ,u.last_name ,t.title_degree,u.id_photo  , ",
        "cc.code AS category_code,cc.category_name AS category_name FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_experiences AS e ON e.id_teacher = t.id ",
        "INNER JOIN tb_m_users AS u ON t.id_user = u.id ",
        "LEFT JOIN tb_r_files AS f ON u.id_photo = f.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ",
        "WHERE current_timestamp < c.period_end AND current_timestamp < c.period_start");
    List<?> listObj = createNativeQuery(sql).getResultList();

    List<Course> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Course course = new Course();
      course.setId((String) objArr[0]);
      course.setCode((String) objArr[1]);

      CourseType courseType = new CourseType();
      courseType.setName((String) objArr[2]);
      course.setCourseType(courseType);

      course.setCapacity((Integer) objArr[3]);
      Timestamp inTime = (Timestamp) objArr[4];
      course.setPeriodStart(inTime.toLocalDateTime());
      inTime = (Timestamp) objArr[5];
      course.setPeriodEnd(inTime.toLocalDateTime());

      Teacher teacher = new Teacher();
      teacher.setId((String) objArr[6]);
      teacher.setCode((String) objArr[7]);
      Experience experience = new Experience();
      experience.setTitle((String) objArr[8]);
      User user = new User();
      user.setFirstName((String) objArr[9]);
      user.setLastName((String) objArr[10]);
      teacher.setTitleDegree((String) objArr[11]);

      File file = new File();
      file.setId((String) objArr[12]);
      user.setUserPhoto(file);

      teacher.setUser(user);

      course.setTeacher(teacher);

      CourseCategory courseCategory = new CourseCategory();
      courseCategory.setCode((String) objArr[13]);
      courseCategory.setName((String) objArr[14]);
      course.setCategory(courseCategory);

      listResult.add(course);
    });

    return listResult;
  }

  @Override
  public List<Course> getCourseByStudentId(String id) throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id AS course_id,c.code AS course_code, ct.type_name AS typeName, c.capacity ,c.period_start",
        " ,c.period_end ,t.id AS teacher_id ,t.code AS teacher_code,u.first_name ,u.last_name ,",
        "t.title_degree ,cc.code AS category_code,cc.category_name AS category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_users AS u ON t.id_user = u.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ",
        "INNER JOIN student_course AS sc ON c.id = sc.id_course ",
        "INNER JOIN tb_m_students AS s ON sc.id_student = s.id WHERE sc.id_student = ?");
    List<Course> listResult = new ArrayList<>();
    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();


    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Course course = new Course();
      course.setId((String) objArr[0]);
      course.setCode((String) objArr[1]);

      CourseType courseType = new CourseType();
      courseType.setName((String) objArr[2]);
      course.setCourseType(courseType);

      course.setCapacity((Integer) objArr[3]);
      Timestamp inTime = (Timestamp) objArr[4];
      course.setPeriodStart(inTime.toLocalDateTime());
      inTime = (Timestamp) objArr[5];
      course.setPeriodEnd(inTime.toLocalDateTime());

      Teacher teacher = new Teacher();
      teacher.setId((String) objArr[6]);
      teacher.setCode((String) objArr[7]);
      User user = new User();
      user.setFirstName((String) objArr[8]);
      user.setLastName((String) objArr[9]);
      teacher.setUser(user);
      teacher.setTitleDegree((String) objArr[10]);
      course.setTeacher(teacher);

      CourseCategory courseCategory = new CourseCategory();
      courseCategory.setCode((String) objArr[11]);
      courseCategory.setName((String) objArr[12]);
      course.setCategory(courseCategory);

      listResult.add(course);
    });
    return listResult;
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    String sql = buildQueryOf("UPDATE tb_m_courses SET is_active = FALSE");
    updateNativeSQL(sql, id, userId);
  }

  @Override
  public List<Course> getCourseForAdmin() throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id AS course_id,c.code AS course_code, ct.type_name AS typeName, c.capacity ,c.status,c.description,c.period_start ,c.period_end,cc.category_name AS category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id");
    List<?> listObj = createNativeQuery(sql).getResultList();
    List<Course> listResult = new ArrayList<>();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Course course = new Course();
      course.setId((String) objArr[0]);
      course.setCode((String) objArr[1]);

      CourseType courseType = new CourseType();
      courseType.setName((String) objArr[2]);
      course.setCourseType(courseType);

      course.setCapacity((Integer) objArr[3]);
      course.setStatus(CourseStatus.valueOf((String) objArr[4]));
      course.setDescription((String) objArr[5]);

      Timestamp inTime = (Timestamp) objArr[6];
      course.setPeriodStart(inTime.toLocalDateTime());
      inTime = (Timestamp) objArr[7];
      course.setPeriodEnd(inTime.toLocalDateTime());

      CourseCategory courseCategory = new CourseCategory();
      courseCategory.setName((String) objArr[8]);
      course.setCategory(courseCategory);

      listResult.add(course);
    });
    return listResult;
  }

  @Override
  public void registerCourse(String course, String student) throws Exception {
    String sql =
        buildQueryOf("INSERT INTO student_course (id_student,id_course) ", "VALUES ", "(?1,?2)");

    createNativeQuery(sql).setParameter(1, student).setParameter(2, course).executeUpdate();
  }

  @Override
  public Course getCourseById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public Map<Course, Integer[]> getTeacherCourse(String id) throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id AS course_id,c.code AS course_code, ct.type_name AS type_name, c.capacity ,c.description, ",
        "(SELECT count(*) FROM student_course sc WHERE sc.id_course = c.id) AS student ,",
        "(SELECT count(*) FROM tb_m_modules m WHERE m.id_course = c.id) AS modul ,c.period_start ,c.period_end ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON ct.id = c.id_course_type ",
        "INNER JOIN tb_m_course_categories AS cc ON cc.id = c.id_category ",
        "INNER JOIN tb_m_teachers AS t ON t.id = c.id_teacher ",
        "INNER JOIN student_course AS sc ON sc.id_course = c.id WHERE t.id = ?1 ",
        "group by course_id , course_code ,type_name , c.capacity  , c.description, c.period_start , c.period_end ");
    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();
    Map<Course, Integer[]> courseMap = new HashMap<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Course course = new Course();
      course.setId((String) objArr[0]);
      course.setCode((String) objArr[1]);

      CourseType courseType = new CourseType();
      courseType.setName((String) objArr[2]);
      course.setCourseType(courseType);

      course.setCapacity((Integer) objArr[3]);
      course.setDescription((String) objArr[4]);

      BigInteger bigInteger = (BigInteger) objArr[5];
      Integer totalStudent = bigInteger.intValue();

      bigInteger = (BigInteger) objArr[6];
      Integer totalModule = bigInteger.intValue();

      Timestamp inTime = (Timestamp) objArr[7];
      course.setPeriodStart(inTime.toLocalDateTime());
      inTime = (Timestamp) objArr[8];
      course.setPeriodEnd(inTime.toLocalDateTime());

      Integer[] value = {totalStudent, totalModule};

      courseMap.put(course, value);
    });
    return courseMap;
  }

  @Override
  public Integer getTotalStudentByIdCourse(String id) throws Exception {
    String sql =
        buildQueryOf("SELECT count(*) FROM student_course sc WHERE sc.id_course = ?1");
    BigInteger bigInteger =
        (BigInteger) createNativeQuery(sql).setParameter(1, id).getSingleResult();
    return bigInteger.intValue();
  }

  @Override
  public Integer getCapacityCourse(String courseId) throws Exception {
    String sql = buildQueryOf("SELECT count(id_student) FROM student_course AS sc ",
        "WHERE sc.id_course = ?1");
    BigInteger bigInteger =
        (BigInteger) createNativeQuery(sql).setParameter(1, courseId).getSingleResult();
    return bigInteger.intValue();

  }

  @Override
  public List<CheckCourseRegisterRequestDTO> checkDataRegisterCourse(String courseId)
      throws Exception {
    String sql =
        buildQueryOf("SELECT id_student,id_course FROM student_course WHERE id_course = ?1");
    List<?> obj = createNativeQuery(sql).setParameter(1, courseId).getResultList();
    List<CheckCourseRegisterRequestDTO> listResult = new ArrayList<>();
    obj.forEach(val -> {
      Object[] arrObj = (Object[]) val;
      CheckCourseRegisterRequestDTO student = new CheckCourseRegisterRequestDTO();
      student.setStudentId((String) arrObj[0]);
      student.setCourseId((String) arrObj[1]);
      listResult.add(student);
    });
    return listResult;
  }

}
