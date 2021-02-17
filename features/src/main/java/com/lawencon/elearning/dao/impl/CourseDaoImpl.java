package com.lawencon.elearning.dao.impl;

import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dto.admin.DashboardCourseResponseDto;
import com.lawencon.elearning.dto.course.CourseProgressResponseDTO;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.CourseStatus;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.Experience;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.util.Callback;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class CourseDaoImpl extends CustomBaseDao<Course> implements CourseDao {

  private final String getCoursesSQL = buildQueryOf(
      "SELECT c.id AS course_id, c.code AS course_code, ct.type_name AS typeName, c.capacity, c.period_start, c.period_end, ",
      "t.id AS teacher_id, t.code AS teacher_code, e.title, u.first_name, u.last_name, t.title_degree, u.id_photo, ",
      "cc.code AS category_code, cc.category_name AS category_name, c.status, c.description FROM tb_m_courses AS c ",
      "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
      "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
      "LEFT JOIN tb_m_experiences AS e ON e.id_teacher = t.id ",
      "INNER JOIN tb_m_users AS u ON t.id_user = u.id ",
      "LEFT JOIN tb_r_files AS f ON u.id_photo = f.id ",
      "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ");

  @Override
  public List<Course> findAll() throws Exception {
    return getAndSetupListCourseByQuery(getCoursesSQL);
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
    return getAndSetupListCourseByQuery(getCoursesSQL
        + "WHERE CURRENT_DATE < c.period_end AND CURRENT_DATE <= c.period_start");
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
        "INNER JOIN tb_m_students AS s ON sc.id_student = s.id WHERE sc.id_student = ?1 ",
        "ORDER BY c.period_start");
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
      Date inTime = (Date) objArr[4];
      course.setPeriodStart(inTime.toLocalDate());
      inTime = (Date) objArr[5];
      course.setPeriodEnd(inTime.toLocalDate());

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
        "SELECT c.id AS course_id,c.code AS course_code, ct.type_name AS typeName, c.capacity ,c.status,c.description,c.period_start ,",
        "c.period_end,cc.category_name AS category_name, ",
        "c.id_course_type, c.id_category, c.id_teacher ",
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
      courseType.setId((String) objArr[9]);
      course.setCourseType(courseType);

      course.setCapacity((Integer) objArr[3]);
      course.setStatus(CourseStatus.valueOf((String) objArr[4]));
      course.setDescription((String) objArr[5]);

      Date inTime = (Date) objArr[6];
      course.setPeriodStart(inTime.toLocalDate());
      inTime = (Date) objArr[7];
      course.setPeriodEnd(inTime.toLocalDate());

      CourseCategory courseCategory = new CourseCategory();
      courseCategory.setName((String) objArr[8]);
      courseCategory.setId((String) objArr[10]);
      course.setCategory(courseCategory);

      Teacher teacher = new Teacher();
      teacher.setId((String) objArr[11]);
      course.setTeacher(teacher);

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

  /**
   * Edited by Galih
   */
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
        "LEFT JOIN student_course AS sc ON sc.id_course = c.id WHERE t.id = ?1 ",
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

      Date inTime = (Date) objArr[7];
      course.setPeriodStart(inTime.toLocalDate());
      inTime = (Date) objArr[8];
      course.setPeriodEnd(inTime.toLocalDate());

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
  public Integer checkDataRegisterCourse(String courseId, String studentId)
      throws Exception {
    String sql =
        buildQueryOf(
            "SELECT count(*) FROM student_course WHERE id_course = ?1 AND id_student = ?2");
    BigInteger bigInteger = (BigInteger) createNativeQuery(sql).setParameter(1, courseId)
        .setParameter(2, studentId).getSingleResult();

    return bigInteger.intValue();
  }

  @Override
  public DashboardCourseResponseDto dashboardCourseByAdmin() throws Exception {
    String sql = buildQueryOf("SELECT COUNT(id) AS all_course, ",
        "SUM(CASE WHEN is_active = TRUE THEN 1 ELSE 0 END) AS course_active, ",
        "SUM(CASE WHEN is_active = FALSE THEN 1 ELSE 0 END) AS course_inactive, ",
        "SUM(CASE WHEN period_end < current_timestamp THEN 1 ELSE 0 END) AS course_expired , ",
        "SUM(CASE WHEN period_start > current_timestamp THEN 1 ELSE 0 END) AS course_available, ",
        "count(DISTINCT id_teacher) AS teacher ",
        "FROM tb_m_courses");
    List<?> objList = createNativeQuery(sql).getResultList();
    DashboardCourseResponseDto dashboardRespon = new DashboardCourseResponseDto();
    objList.forEach(val -> {
      Object[] objArr = (Object[]) val;
      dashboardRespon.setTotal(((BigInteger) objArr[0]).intValue());
      dashboardRespon.setActive(((BigInteger) objArr[1]).intValue());
      dashboardRespon.setInactive(((BigInteger) objArr[2]).intValue());
      dashboardRespon.setExpired(((BigInteger) objArr[3]).intValue());
      dashboardRespon.setAvailable(((BigInteger) objArr[4]).intValue());
      dashboardRespon.setRegisteredTeacher(((BigInteger) objArr[5]).intValue());
    });
    return dashboardRespon;
  }


  /**
   * @param query sql query for get course list
   * @return list course of query result
   * @author Rian Rivaldo
   */
  private List<Course> getAndSetupListCourseByQuery(String query) {
    List<?> listObj = createNativeQuery(query).getResultList();
    if (listObj.isEmpty()) {
      return Collections.emptyList();
    }
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
      Date inTime = (Date) objArr[4];
      course.setPeriodStart(inTime.toLocalDate());
      inTime = (Date) objArr[5];
      course.setPeriodEnd(inTime.toLocalDate());

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

      course.setStatus(CourseStatus.valueOf((String) objArr[15]));
      course.setDescription((String) objArr[16]);

      listResult.add(course);
    });
    return listResult;
  }

  @Override
  public Integer getRegisterStudent() throws Exception {
    String sql = "SELECT COUNT(distinct id_student) from student_course";
    return ((BigInteger) createNativeQuery(sql).getSingleResult()).intValue();
  }

  @Override
  public List<CourseAttendanceReportByTeacher> getCourseAttendanceReport(String courseId)
      throws Exception {
    String sql = buildQueryOf("SELECT m.title , s.schedule_date , ",
        "count(a.id_student) AS student_present FROM tb_r_attendances AS a ",
        "RIGHT JOIN tb_m_modules AS m ON m.id = a.id_module ",
        "INNER JOIN tb_m_schedules AS s ON s.id = m.id_schedule ",
        "INNER JOIN tb_m_courses AS c ON c.id = m.id_course WHERE c.id = ?1 ",
        "GROUP BY m.title ,s.schedule_date,c.capacity");
    List<?> listObj = createNativeQuery(sql).setParameter(1, courseId).getResultList();

    List<CourseAttendanceReportByTeacher> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] arrObj = (Object[]) val;
      CourseAttendanceReportByTeacher object = new CourseAttendanceReportByTeacher();
      object.setModuleName((String) arrObj[0]);

      Date date = (Date) arrObj[1];
      object.setDate(date.toLocalDate().toString());

      BigInteger bigInteger = (BigInteger) arrObj[2];
      object.setPresent(bigInteger.intValue());
      listResult.add(object);
    });
    return listResult;
  }

  @Override
  public List<CourseProgressResponseDTO> getCourseProgressByStudentId(String studentId)
      throws Exception {
    String sql =
        buildQueryOf("SELECT c.id ,ct.type_name,c.period_end,c.period_start ,count(DISTINCT m.id) ",
            "FROM tb_m_courses AS c ",
            "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
            "INNER JOIN student_course AS sc ON c.id = sc.id_course ",
            "LEFT JOIN tb_m_modules AS m ON m.id_course = c.id WHERE sc.id_student = ?1 ",
            "GROUP BY c.id ,ct.type_name ",
            "ORDER BY c.period_start");

    List<?> listObj = createNativeQuery(sql).setParameter(1, studentId).getResultList();
    List<CourseProgressResponseDTO> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      CourseProgressResponseDTO courseProgress = new CourseProgressResponseDTO();
      courseProgress.setCourseId((String) objArr[0]);
      courseProgress.setCourseName((String) objArr[1]);
      courseProgress.setPeriodEnd(((Date) objArr[2]).toLocalDate());
      courseProgress.setPeriodStart(((Date) objArr[3]).toLocalDate());
      courseProgress.setTotalModule(((BigInteger) objArr[4]).intValue());
      listResult.add(courseProgress);
    });
    return listResult;
  }

  @Override
  public Integer getModuleCompleteByStudentId(String courseId, String studentId) throws Exception {
    String sql =
        buildQueryOf("SELECT count(DISTINCT m.id) FROM tb_m_modules AS m ",
            "INNER JOIN tb_m_subject_categories AS sc ON sc.id = m.id_subject ",
            "LEFT JOIN tb_r_attendances AS a ON a.id_module = m.id ",
            "LEFT JOIN tb_m_schedules AS s ON s.id = m.id_schedule ",
            "WHERE m.id_course = ?1 AND now() > s.schedule_date AND a.id_student = ?2 ");
    return ((BigInteger) createNativeQuery(sql).setParameter(1, courseId).setParameter(2, studentId)
        .getSingleResult()).intValue();
  }

}
