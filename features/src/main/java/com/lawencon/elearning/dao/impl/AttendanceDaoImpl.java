package com.lawencon.elearning.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.User;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
@Repository
public class AttendanceDaoImpl extends CustomBaseDao<Attendance> implements AttendanceDao {

  @Override
  public void createAttendance(Attendance data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public void verifyAttendance(Attendance data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public List<Attendance> getAttendanceList(String idCourse, String idModule) throws Exception {
    String query = buildQueryOf(
        "SELECT COALESCE(a.id, '0'), COALESCE(a.created_at, '0001-01-01 00:00') as att_time, ",
        "COALESCE(a.VERSION, 0) as att_version, COALESCE(a.is_verified, FALSE) as att_verify, ",
        "u.first_name, u.last_name ",
        "FROM tb_r_attendances a ", "RIGHT JOIN tb_m_students s ON s.id = a.id_student ",
        "INNER JOIN tb_m_users u ON u.id = s.id_user ",
        "INNER JOIN student_course sc ON sc.id_student = s.id ", "WHERE sc.id_course = ?1 ",
        "AND a.id_module = ?2 ", "OR a.id_module IS NULL ");
    List<?> listObj = createNativeQuery(query).setParameter(1, idCourse).setParameter(2, idModule)
        .getResultList();
    List<Attendance> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Attendance attendance = new Attendance();
      attendance.setId((String) objArr[0]);
      Timestamp inTime = (Timestamp) objArr[1];
      attendance.setCreatedAt(inTime.toLocalDateTime());
      attendance.setVersion(Long.valueOf(objArr[2].toString()));
      attendance.setIsVerified((boolean) objArr[3]);

      User user = new User();
      user.setFirstName((String) objArr[4]);
      user.setLastName((String) objArr[5]);
      Student student = new Student();
      student.setUser(user);
      attendance.setStudent(student);
      listResult.add(attendance);
    });
    return listResult;
  }

  @Override
  public Attendance getAttendanceById(String id) throws Exception {
    return getById(id);
  }

}
