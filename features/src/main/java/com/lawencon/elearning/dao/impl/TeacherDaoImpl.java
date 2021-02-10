package com.lawencon.elearning.dao.impl;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.TeacherDao;
import com.lawencon.elearning.dto.teacher.CourseAttendanceReportByTeacher;
import com.lawencon.elearning.dto.teacher.TeacherForAdminDTO;
import com.lawencon.elearning.dto.teacher.TeacherProfileDTO;
import com.lawencon.elearning.dto.teacher.TeacherReportResponseDTO;
import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

/**
 * @author Dzaky Fadhilla Guci
 */

@Repository
public class TeacherDaoImpl extends CustomBaseDao<Teacher> implements TeacherDao {

  @Override
  public List<Teacher> getAllTeachers() throws Exception {
    return getAll();
  }

  @Override
  public List<TeacherForAdminDTO> allTeachersForAdmin() throws Exception {
    String sql = buildQueryOf("SELECT tmt.id , tmt.code , tmu.first_name , tmu.last_name , ",
        "tmt.phone , tmt.gender , tmu.username , tmt.is_active , tmu.email, tmt.title_degree, tmu.id_photo, tmt.version ",
        "FROM tb_m_teachers tmt ",
        "INNER JOIN tb_m_users tmu ON tmt.id_user = tmu.id");

    List<?> listObj = createNativeQuery(sql).getResultList();

    List<TeacherForAdminDTO> teachers = new ArrayList<>();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      TeacherForAdminDTO teacher = new TeacherForAdminDTO();
      teacher.setId((String) objArr[0]);
      teacher.setCode((String) objArr[1]);
      teacher.setFirstName((String) objArr[2]);
      teacher.setLastName((String) objArr[3]);
      teacher.setPhone((String) objArr[4]);
      teacher.setGender(Gender.valueOf((String) objArr[5]));
      teacher.setUsername((String) objArr[6]);
      teacher.setActive((boolean) objArr[7]);
      teacher.setEmail((String) objArr[8]);
      teacher.setTitleDegree((String) objArr[9]);
      teacher.setPhotoId((String) objArr[10]);
      teacher.setVersion(Long.valueOf(objArr[11].toString()));

      teachers.add(teacher);
    });

    return teachers.size() > 0 ? teachers : null;
  }

  @Override
  public void saveTeacher(Teacher data, Callback before) throws Exception {
    save(data, before, null, false, false);
  }


  @Override
  public Teacher findTeacherById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public TeacherProfileDTO findTeacherByIdCustom(String id) throws Exception {

    String sql = buildQueryOf(
        "SELECT tmt.id as id_techer, tmu.first_name , tmu.last_name , tmu.email , tmt.title_degree , tmt.created_at, ",
        "tmt.gender , tmu.id_photo, tmt.phone FROM tb_m_teachers tmt ",
        "INNER JOIN tb_m_users tmu ON tmt.id_user = tmu.id WHERE tmt.id=?")
            .toString();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    List<TeacherProfileDTO> listTeachers =
        HibernateUtils.bMapperList(listObj, TeacherProfileDTO.class, "id", "firstName", "lastName",
            "email", "titleDegree", "createdAt", "gender", "photoId", "phone");

    return listTeachers.size() > 0 ? listTeachers.get(0) : null;
  }

  @Override
  public void updateIsActive(String id, String userId, boolean status) throws Exception {
    String sql = buildQueryOf(
        "UPDATE tb_m_teachers SET is_active = ?1 ",
        ", updated_at = now(), updated_by = ?2 , version = (version + 1) WHERE id = ?3")
            .toString();

    createNativeQuery(sql).setParameter(1, status).setParameter(2, userId).setParameter(3, id)
        .executeUpdate();
  }

  @Override
  public void updateTeacher(Teacher data, Callback before) throws Exception {
    save(data, before, null, false, false);

  }

  @Override
  public Teacher findByUserId(String userId) throws Exception {
    String sql = buildQueryOf(
        "SELECT tmt.phone , tmt.gender ",
        "FROM tb_m_teachers tmt ",
        "WHERE id_user = ?1 ").toString();


    List<?> listObj = createNativeQuery(sql).setParameter(1, userId).getResultList();

    List<Teacher> listResult =
        HibernateUtils.bMapperList(listObj, Teacher.class, "phone", "gender");

    return getResultModel(listResult);
  }

  @Override
  public void deleteTeacherById(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public Teacher findByIdForCourse(String id) throws Exception {
    String sql = buildQueryOf("SELECT tmt.id AS teacher_id , tmu.first_name , ",
        "tmu.last_name , tmt.title_degree, tmt.version FROM tb_m_teachers tmt ",
        "INNER JOIN tb_m_users tmu ON tmu.id = tmt.id_user").toString();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    List<Teacher> listResult =
        HibernateUtils.bMapperList(listObj, Teacher.class, "id", "user.firstName", "user.lastName",
            "titleDegree", "version");

    return getResultModel(listResult);
  }

  @Override
  public List<String> checkConstraint(String id) throws Exception {
    String sql = buildQueryOf("SELECT tmc.id_teacher AS course , tms.id_teacher AS schedule , ",
        "tme.id_teacher AS experience FROM tb_m_teachers tmt ",
        "LEFT JOIN tb_m_courses tmc ON tmt.id = tmc.id_teacher ",
        "LEFT JOIN tb_m_schedules tms  ON tmt.id = tms.id_teacher ",
        "LEFT JOIN tb_m_experiences tme ON tmt.id = tme.id_teacher ",
        "WHERE tmt.id = ?1 LIMIT 1").toString();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();
    List<String> strings = new ArrayList<String>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      strings.add((String) objArr[0]);
      strings.add((String) objArr[1]);
      strings.add((String) objArr[2]);

    });
    return strings.size() > 0 ? strings : null;
  }

  @Override
  public String getUserId(String teacherId) throws Exception {
    String sql = "SELECT id_user FROM tb_m_teachers WHERE id = ?1";
    List<?> objList = createNativeQuery(sql).setParameter(1, teacherId).getResultList();
    List<String> resultList = new ArrayList<>();
    objList.forEach(val -> {
      Object[] objArr = (Object[]) val;
      resultList.add((String) objArr[0]);
    });
    return (resultList.size() != 0 ? resultList.get(0) : null);
  }

  @Override
  public List<TeacherReportResponseDTO> getTeacherDetailCourseReport(String moduleId)
      throws Exception {
    String sql = buildQueryOf(
        "SELECT s.code , u.first_name , u.last_name, count(e.id_module) AS total_ujian,count(de.id_exam) AS assign, ",
        "count(e.id_module)-count(de.id_exam) AS unassignment , avg(de.grade) AS nilai_avg ",
        "FROM tb_r_dtl_exams AS de INNER JOIN tb_r_exams AS e ON de.id_exam = e.id ",
        "INNER JOIN tb_m_modules AS m ON m.id = e.id_module ",
        "INNER JOIN tb_m_students AS s ON s.id  = de.id_student ",
        "INNER JOIN tb_m_users AS u ON u.id = s.id_user WHERE m.id = ?1 ",
        "GROUP BY s.code ,u.first_name ,u.last_name");

    List<?> listObj = createNativeQuery(sql).setParameter(1, moduleId).getResultList();
    List<TeacherReportResponseDTO> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] arrObj = (Object[]) val;
      TeacherReportResponseDTO responseDTO = new TeacherReportResponseDTO();
      responseDTO.setStudentCode((String) arrObj[0]);
      responseDTO.setStudentFirstName((String) arrObj[1]);
      responseDTO.setStudentLastName((String) arrObj[2]);
      BigInteger bigInteger = (BigInteger) arrObj[3];
      responseDTO.setTotalUjian((Integer) bigInteger.intValue());
      bigInteger = (BigInteger) arrObj[4];
      responseDTO.setTotalAssignment((Integer) bigInteger.intValue());
      bigInteger = (BigInteger) arrObj[5];
      responseDTO.setNotAssignment((Integer) bigInteger.intValue());
      responseDTO.setAvgScore((Double) arrObj[6]);
      listResult.add(responseDTO);
    });
    return listResult;
  }

  @Override
  public List<CourseAttendanceReportByTeacher> getCourseAttendanceReport(String courseId)
      throws Exception {
    String sql = buildQueryOf("SELECT ct.type_name , m.title , s.schedule_date , ",
        "count(a.id_student) AS student_present FROM tb_r_attendances AS a ",
        "RIGHT JOIN tb_m_modules AS m ON m.id = a.id_module ",
        "INNER JOIN tb_m_schedules AS s ON s.id = m.id_schedule ",
        "INNER JOIN tb_m_courses AS c ON c.id = m.id_course ",
        "INNER JOIN tb_m_course_types AS ct ON ct.id = c.id_course_type WHERE c.id = ?1 ",
        "GROUP BY ct.type_name , m.title ,s.schedule_date,c.capacity");
    List<?> listObj = createNativeQuery(sql).setParameter(1,courseId).getResultList();
    List<CourseAttendanceReportByTeacher> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] arrObj = (Object[]) val;
      CourseAttendanceReportByTeacher object = new CourseAttendanceReportByTeacher();
      object.setCourseName((String) arrObj[0]);
      object.setModuleName((String) arrObj[1]);

      Date date = (Date) arrObj[2];
      object.setDate(date.toLocalDate().toString());

      BigInteger bigInteger = (BigInteger) arrObj[3];
      object.setPresent(bigInteger.intValue());
      listResult.add(object);
    });
    return listResult;
  }

  @Override
  public Integer getTotalStudentByIdCourse(String courseId) throws Exception {
    String sql = buildQueryOf("SELECT count(sc.id_student) FROM student_course AS sc ",
        "WHERE sc.id_course = ?1");
    BigInteger bigInteger =
        (BigInteger) createNativeQuery(sql).setParameter(1, courseId).getSingleResult();
    return bigInteger.intValue();
  }

}
