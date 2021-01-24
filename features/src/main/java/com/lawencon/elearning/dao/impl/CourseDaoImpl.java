package com.lawencon.elearning.dao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.Teacher;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class CourseDaoImpl extends BaseDaoImpl<Course> implements CourseDao, BaseCustomDao {

  @Override
  public List<Course> getListCourse() throws Exception {
    return getAll();
  }

  @Override
  public void insertCourse(Course course) throws Exception {
    save(course, null, null, true, true);
  }

  @Override
  public void updateCourse(Course course) throws Exception {
    save(course, null, null, true, true);
  }

  @Override
  public void deleteCourse(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public List<Course> getCurentAvailableCourse() throws Exception {
    String sql = bBuilder(
        "SELECT c.id ,c.code, ct.type_name AS courseName, c.capacity ,c.period_start ,c.period_end ,t.id ,t.code ,t.first_name ,t.last_name ,t.title_degree ,cc.code ,cc.category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ",
        "WHERE current_timestamp < c.period_end").toString();
    List<Course> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).getResultList();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Course course = new Course();
      course.setId((String) objArr[0]);
      course.setCode((String) objArr[1]);
      CourseType courseType = new CourseType();
      courseType.setName((String) objArr[2]);
      course.setCourseType(courseType);
      course.setCapacity((Integer) objArr[3]);
      course.setPeriodStart((LocalDateTime) objArr[4]);
      course.setPeriodEnd((LocalDateTime) objArr[5]);

      Teacher teacher = new Teacher();
      teacher.setId((String) objArr[6]);
      teacher.setCode((String) objArr[7]);
      teacher.setFirstName((String) objArr[8]);
      teacher.setLastName((String) objArr[9]);
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
  public List<Course> getMyCourse() throws Exception {
    String sql = bBuilder(
        "SELECT c.id ,c.code, ct.type_name AS courseName, c.capacity ,c.period_start ,c.period_end ,t.id ,t.code ,t.first_name ,t.last_name ,t.title_degree ,cc.code ,cc.category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ",
        "INNER JOIN student_course AS sc ON c.id = sc.course_id ",
        "INNER JOIN tb_m_students AS s ON sc.student_id = s.id WHERE s.id = sc.student_id")
            .toString();
    List<Course> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).getResultList();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Course course = new Course();
      course.setId((String) objArr[0]);
      course.setCode((String) objArr[1]);

      CourseType courseType = new CourseType();
      courseType.setName((String) objArr[2]);
      course.setCourseType(courseType);

      course.setCapacity((Integer) objArr[3]);
      course.setPeriodStart((LocalDateTime) objArr[4]);
      course.setPeriodEnd((LocalDateTime) objArr[5]);

      Teacher teacher = new Teacher();
      teacher.setId((String) objArr[6]);
      teacher.setCode((String) objArr[7]);
      teacher.setFirstName((String) objArr[8]);
      teacher.setLastName((String) objArr[9]);
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
  public void softDelete(String id) throws Exception {
    String sql =
        bBuilder("UPDATE tb_m_courses SET is_active = FALSE WHERE id =?1 ").toString();
    createNativeQuery(sql).setParameter(1, id).executeUpdate();

  }

  @Override
  public List<Course> getCourseForAdmin() throws Exception {
    String sql = bBuilder(
        "SELECT c.id ,c.code, ct.type_name AS courseName, c.capacity ,c.status ,c.descripton ,c.period_start ,c.period_end ,t.id ,t.code ,t.first_name ,t.last_name ,t.title_degree ,cc.code ,cc.category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id").toString();
    List<Course> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).getResultList();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Course course = new Course();
      course.setId((String) objArr[0]);
      course.setCode((String) objArr[1]);

      CourseType courseType = new CourseType();
      courseType.setName((String) objArr[2]);
      course.setCourseType(courseType);

      course.setCapacity((Integer) objArr[3]);
      course.setPeriodStart((LocalDateTime) objArr[4]);
      course.setPeriodEnd((LocalDateTime) objArr[5]);

      Teacher teacher = new Teacher();
      teacher.setId((String) objArr[6]);
      teacher.setCode((String) objArr[7]);
      teacher.setFirstName((String) objArr[8]);
      teacher.setLastName((String) objArr[9]);
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
  public void registerCourse(Course course) throws Exception {
    save(course, null, null, true, true);
  }

}
