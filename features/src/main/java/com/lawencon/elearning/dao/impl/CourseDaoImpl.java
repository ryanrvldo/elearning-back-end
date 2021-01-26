package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.util.HibernateUtils;
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
  public List<Course> getCurentAvailableCourse() throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id ,c.code, ct.type_name AS courseName, c.capacity ,c.period_start ,c.period_end ,t.id ,t.code ,t.first_name ,t.last_name ,t.title_degree ,cc.code ,cc.category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ",
        "WHERE current_timestamp < c.period_end").toString();
    List<?> listObj = createNativeQuery(sql).getResultList();

    List<Course> listResult = HibernateUtils.bMapperList(listObj, Course.class, "id", "code",
        "courseType.name",
        "capacity", "periodStart", "periodEnd", "teacher.id", "teacher.code", "teacher.firstName",
        "teacher.lastName", "teacher.titleDegree", "category.code", "category.name");
    return listResult;
  }

  @Override
  public List<Course> getMyCourse() throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id ,c.code, ct.type_name AS courseName, c.capacity ,c.period_start ,c.period_end ,t.id ,t.code ,t.first_name ,t.last_name ,t.title_degree ,cc.code ,cc.category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ",
        "INNER JOIN student_course AS sc ON c.id = sc.course_id ",
        "INNER JOIN tb_m_students AS s ON sc.student_id = s.id WHERE s.id = sc.student_id")
            .toString();

    List<?> listObj = createNativeQuery(sql).getResultList();
    List<Course> listResult = HibernateUtils.bMapperList(listObj, Course.class, "id", "code",
        "courseType.name",
        "capacity", "periodStart", "periodEnd", "teacher.id", "teacher.code", "teacher.firstName",
        "teacher.lastName", "teacher.titleDegree", "category.code", "category.name");
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
        "SELECT c.id ,c.code, ct.type_name AS courseName, c.capacity ,c.status ,c.descripton ,c.period_start ,c.period_end ,t.id ,t.code ,t.first_name ,t.last_name ,t.title_degree ,cc.code ,cc.category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id").toString();

    List<?> listObj = createNativeQuery(sql).getResultList();
    List<Course> listResult =
        HibernateUtils.bMapperList(listObj, Course.class, "id", "code", "courseType.name",
        "capacity", "status", "description", "periodStart", "periodEnd", "teacher.id",
        "teacher.code", "teacher.firstName", "teacher.lastName", "teacher.titleDegree",
        "category.code", "category.name");
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
