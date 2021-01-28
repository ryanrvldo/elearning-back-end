package com.lawencon.elearning.dao.impl;

import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.CourseStatus;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.util.Callback;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

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
  public List<Course> getCurentAvailableCourse() throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id ,c.code, ct.type_name AS courseName, c.capacity ,c.period_start ,c.period_end ,t.id ,t.code ,u.first_name ,u.last_name ,t.title_degree ,cc.code ,cc.category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_users AS u ON t.id_user = u.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ",
        "INNER JOIN student_course AS sc ON c.id = sc.id_course ",
        "INNER JOIN tb_m_students AS s ON sc.id_student = s.id ", "WHERE s.id = sc.id_student")
            .toString();
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
  public List<Course> getMyCourse(String id) throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id ,c.code, ct.type_name AS typeName, c.capacity ,c.period_start ,c.period_end ,t.id ,t.code ,u.first_name ,u.last_name ,t.title_degree ,cc.code ,cc.category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_users AS u ON t.id_user = u.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ",
        "INNER JOIN student_course AS sc ON c.id = sc.course_id ",
        "INNER JOIN tb_m_students AS s ON sc.student_id = s.id WHERE s.id = ?")
            .toString();
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
    String sql =
        buildQueryOf("UPDATE tb_m_courses SET is_active = FALSE").toString();
    updateNativeSQL(sql, id, userId);
  }

  @Override
  public List<Course> getCourseForAdmin() throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id ,c.code, ct.type_name AS courseName, c.capacity ,c.status ,c.descripton ,c.period_start ,c.period_end ,t.id ,t.code ,u.first_name ,u.last_name ,t.title_degree ,cc.code ,cc.category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_users AS u ON t.id_user = u.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id").toString();

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

      Timestamp inTime = (Timestamp) objArr[4];
      course.setPeriodStart(inTime.toLocalDateTime());
      inTime = (Timestamp) objArr[5];
      course.setPeriodEnd(inTime.toLocalDateTime());

      Teacher teacher = new Teacher();
      teacher.setId((String) objArr[8]);
      teacher.setCode((String) objArr[9]);
      User user = new User();
      user.setFirstName((String) objArr[10]);
      user.setLastName((String) objArr[11]);
      teacher.setUser(user);
      teacher.setTitleDegree((String) objArr[12]);
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
  public void registerCourse(Course course) throws Exception {
    save(course, null, null, true, true);
  }

  @Override
  public Course getCourseById(String id) throws Exception {
    return getById(id);
  }

}
