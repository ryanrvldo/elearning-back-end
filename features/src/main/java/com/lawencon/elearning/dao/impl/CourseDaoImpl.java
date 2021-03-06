package com.lawencon.elearning.dao.impl;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CourseDao;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dto.admin.DashboardCourseResponseDto;
import com.lawencon.elearning.dto.course.CourseProgressResponseDTO;
import com.lawencon.elearning.dto.course.UpdateStatusRequestDTO;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.CourseCategory;
import com.lawencon.elearning.model.CourseStatus;
import com.lawencon.elearning.model.CourseType;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class CourseDaoImpl extends CustomBaseDao<Course> implements CourseDao {

  private final String getCoursesSQL = buildQueryOf(
      "SELECT c.id AS course_id, c.code AS course_code, ct.type_name AS typeName, c.capacity, c.period_start, c.period_end, ",
      "t.id AS teacher_id, t.code AS teacher_code, u.first_name, u.last_name, t.title_degree, u.id_photo, ",
      "cc.code AS category_code, cc.category_name AS category_name, c.status, c.description FROM tb_m_courses AS c ",
      "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
      "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
      "INNER JOIN tb_m_users AS u ON t.id_user = u.id ",
      "LEFT JOIN tb_r_files AS f ON u.id_photo = f.id ",
      "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ");

  @Override
  public List<Course> findAll() throws Exception {
    return getAndSetupListCourseByQuery(getCoursesSQL + "ORDER BY c.period_start ");
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
        + "WHERE CURRENT_DATE < c.period_end AND CURRENT_DATE <= c.period_start ORDER BY c.period_start ");
  }

  @Override
  public List<Course> getCourseByStudentId(String id) throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id AS course_id,c.code AS course_code, ct.type_name AS typeName, c.capacity ,c.period_start",
        " ,c.period_end ,t.id AS teacher_id ,t.code AS teacher_code,u.first_name ,u.last_name,u.id_photo ,",
        "t.title_degree ,cc.code AS category_code,cc.category_name AS category_name ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_teachers AS t ON c.id_teacher = t.id ",
        "INNER JOIN tb_m_users AS u ON t.id_user = u.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ",
        "INNER JOIN student_course AS sc ON c.id = sc.id_course ",
        "INNER JOIN tb_m_students AS s ON sc.id_student = s.id WHERE sc.id_student = ?1 ",
        "AND sc.is_verified  = true ORDER BY c.period_start");
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
      File file = new File();
      file.setId((String) objArr[10]);
      user.setUserPhoto(file);
      teacher.setUser(user);
      teacher.setTitleDegree((String) objArr[11]);
      course.setTeacher(teacher);

      CourseCategory courseCategory = new CourseCategory();
      courseCategory.setCode((String) objArr[12]);
      courseCategory.setName((String) objArr[13]);
      course.setCategory(courseCategory);

      listResult.add(course);
    });
    return listResult;
  }

  @Override
  public void updateIsActive(String id, String userId, boolean status) throws Exception {
    String sql = buildQueryOf("UPDATE tb_m_courses SET is_active = ?1 ",
        ", updated_at = now(), updated_by = ?2 , version = (version + 1) WHERE id = ?3").toString();

    createNativeQuery(sql).setParameter(1, status).setParameter(2, userId).setParameter(3, id)
        .executeUpdate();
  }

  @Override
  public List<Course> getCourseForAdmin() throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id AS course_id,c.code AS course_code, ct.type_name AS typeName, c.capacity ,c.status,c.description,c.period_start , ",
        "c.period_end,cc.category_name AS category_name, ",
        "c.id_course_type, c.id_category, c.id_teacher ,c.is_active ,u.first_name ,u.last_name ",
        "FROM tb_m_courses AS c ", "INNER JOIN tb_m_teachers AS t ON t.id = c.id_teacher ",
        "INNER JOIN tb_m_users AS u ON u.id = t.id_user ",
        "INNER JOIN tb_m_course_types AS ct ON c.id_course_type = ct.id ",
        "INNER JOIN tb_m_course_categories AS cc ON c.id_category = cc.id ",
        "ORDER BY c.period_start ");
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
      course.setIsActive((Boolean) objArr[12]);
      User user = new User();
      user.setFirstName((String) objArr[13]);
      user.setLastName((String) objArr[14]);
      teacher.setUser(user);

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
    String sql = buildQueryOf(
        "SELECT c.id ,ct.type_name ,c.capacity ,c.code ,c.created_at,c.created_by ,c.description ",
        ",c.id_category ,c.id_course_type ,c.id_teacher ",
        ",c.is_active ,c.period_end,c.period_start ,c.status ,c.updated_at ,c.updated_by ,c.\"version\" ",
        ", u.first_name ,u.last_name ,u.id_photo ,u.email ,t.gender ,t.phone ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON ct.id = c.id_course_type ",
        "INNER JOIN tb_m_teachers AS t ON t.id = c.id_teacher ",
        "INNER JOIN tb_m_users AS u ON u.id = t.id_user WHERE c.id = ?1 ");
    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();
    List<Course> listResult = new ArrayList<>();

    listObj.forEach(val -> {
      Object[] arrObj = (Object[]) val;
      Course course = new Course();
      course.setId((String) arrObj[0]);

      CourseType courseType = new CourseType();
      courseType.setName((String) arrObj[1]);

      course.setCapacity((Integer) arrObj[2]);

      course.setCode((String) arrObj[3]);

      Timestamp time = (Timestamp) arrObj[4];
      course.setCreatedAt(time != null ? time.toLocalDateTime() : null);

      course.setCreatedBy((String) arrObj[5]);
      course.setDescription((String) arrObj[6]);

      CourseCategory category = new CourseCategory();
      category.setId((String) arrObj[7]);
      course.setCategory(category);

      courseType.setId((String) arrObj[8]);
      course.setCourseType(courseType);

      Teacher teacher = new Teacher();
      teacher.setId((String) arrObj[9]);
      course.setIsActive((Boolean) arrObj[10]);

      Date date = (Date) arrObj[11];
      course.setPeriodEnd(date.toLocalDate());

      date = (Date) arrObj[12];
      course.setPeriodStart(date.toLocalDate());
      course.setStatus(CourseStatus.valueOf((String) arrObj[13]));

      time = (Timestamp) arrObj[14];
      course.setUpdatedAt(time != null ? time.toLocalDateTime() : null);
      course.setUpdatedBy((String) arrObj[15]);

      course.setVersion(((BigInteger) arrObj[16]).longValue());

      User user = new User();
      user.setFirstName((String) arrObj[17]);
      user.setLastName((String) arrObj[18]);

      File file = new File();
      file.setId((String) arrObj[19]);
      user.setUserPhoto(file);

      user.setEmail((String) arrObj[20]);

      teacher.setGender(Gender.valueOf((String) arrObj[21]));
      teacher.setPhone((String) arrObj[22]);
      teacher.setUser(user);

      course.setTeacher(teacher);
      listResult.add(course);
    });
    return getResultModel(listResult);
  }

  @Override
  public SortedMap<Course, Integer[]> getTeacherCourse(String id) throws Exception {
    String sql = buildQueryOf(
        "SELECT c.id AS course_id,c.code AS course_code, ct.type_name AS type_name, c.capacity ,c.description, ",
        "(SELECT count(*) FROM student_course sc WHERE sc.id_course = c.id AND sc.is_verified = TRUE) AS student ,",
        "(SELECT count(*) FROM tb_m_modules m WHERE m.id_course = c.id) AS modul ,c.period_start ,c.period_end ",
        "FROM tb_m_courses AS c ",
        "INNER JOIN tb_m_course_types AS ct ON ct.id = c.id_course_type ",
        "INNER JOIN tb_m_course_categories AS cc ON cc.id = c.id_category ",
        "INNER JOIN tb_m_teachers AS t ON t.id = c.id_teacher ",
        "LEFT JOIN student_course AS sc ON sc.id_course = c.id WHERE t.id = ?1 ",
        "group by course_id , course_code ,type_name , c.capacity  , c.description, c.period_start , c.period_end ",
        "ORDER BY c.period_start");
    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();
    SortedMap<Course, Integer[]> courseMap =
        new TreeMap<>(Comparator.comparing(Course::getPeriodStart));
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
        buildQueryOf(
            "SELECT count(*) FROM student_course sc WHERE sc.id_course = ?1 and is_verified = true");
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
      // Experience experience = new Experience();
      // experience.setTitle((String) objArr[8]);
      User user = new User();
      user.setFirstName((String) objArr[8]);
      user.setLastName((String) objArr[9]);
      teacher.setTitleDegree((String) objArr[10]);

      File file = new File();
      file.setId((String) objArr[11]);
      user.setUserPhoto(file);

      teacher.setUser(user);

      course.setTeacher(teacher);

      CourseCategory courseCategory = new CourseCategory();
      courseCategory.setCode((String) objArr[12]);
      courseCategory.setName((String) objArr[13]);
      course.setCategory(courseCategory);

      course.setStatus(CourseStatus.valueOf((String) objArr[14]));
      course.setDescription((String) objArr[15]);

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
    String sql = buildQueryOf("SELECT m.title,s.schedule_date, m.id "
        , "FROM tb_m_modules AS m "
        , "INNER JOIN tb_m_schedules AS s ON s.id = m.id_schedule "
        , "WHERE m.id_course = ?1 ");
    List<?> listObj = createNativeQuery(sql).setParameter(1, courseId).getResultList();

    List<CourseAttendanceReportByTeacher> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] arrObj = (Object[]) val;
      CourseAttendanceReportByTeacher object = new CourseAttendanceReportByTeacher();
      object.setModuleName((String) arrObj[0]);

      Date date = (Date) arrObj[1];
      object.setDate(date != null ? date.toLocalDate().toString() : null);
      object.setModuleId((String) arrObj[2]);
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
            "WHERE m.id_course = ?1 AND now() > s.schedule_date + s.end_time AND a.id_student = ?2 ",
            "AND a.is_verified = TRUE");
    return ((BigInteger) createNativeQuery(sql).setParameter(1, courseId).setParameter(2, studentId)
        .getSingleResult()).intValue();
  }

  @Override
  public void updateCoursesStatus(UpdateStatusRequestDTO data) throws Exception {
    String sql = buildQueryOf("UPDATE tb_m_courses SET status = ?1 ",
        ", updated_at = now(), updated_by = ?2 , version = (version + 1) WHERE id = ?3").toString();

    createNativeQuery(sql).setParameter(1, data.getStatus()).setParameter(2, data.getUpdatedBy())
        .setParameter(3, data.getId()).executeUpdate();
  }

  @Override
  public Integer getStudentPresentOnModule(String moduleId) {
    String sql = buildQueryOf("SELECT count(a.id_student) "
        , "FROM tb_r_attendances AS a "
        , "WHERE a.id_module = ?1 AND a.is_verified = TRUE ");
    return ((BigInteger) createNativeQuery(sql).setParameter(1, moduleId).getSingleResult())
        .intValue();
  }

}
