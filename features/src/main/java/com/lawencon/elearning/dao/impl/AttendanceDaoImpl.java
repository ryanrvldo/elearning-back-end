package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.elearning.util.HibernateUtils;
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
  public void verifAttendance(String id) throws Exception {
    String query = "UPDATE tb_r_attendances SET is_verified = true WHERE id = ?";
    createNativeQuery(query).setParameter(1, id).executeUpdate();
  }

  @Override
  public List<Attendance> getAttendanceList(String idModule) throws Exception {
    String query = buildQueryOf("SELECT a.created_at, a.is_verified, s.first_name, s.last_name ",
        "FROM tb_r_attendances a ", "INNER JOIN tb_m_students s ON s.id = a.id_student ",
        "WHERE a.id_module = ?").toString();
    List<?> listObj = createNativeQuery(query).setParameter(1, idModule).getResultList();
    return HibernateUtils.bMapperList(listObj, Attendance.class, "createdAt", "isVerified",
        "student.firstName", "student.lastName");
    // listObj.forEach(val -> {
    // Object[] objArr = (Object[]) val;
    // Attendance atd = new Attendance();
    // Timestamp attTime = (Timestamp) objArr[0];
    // atd.setCreatedAt((LocalDateTime) attTime.toLocalDateTime());
    // atd.setIsVerified((boolean) objArr[1]);
    // Student std = new Student();
    // std.setFirstName((String) objArr[2]);
    // std.setLastName((String) objArr[3]);
    // atd.setStudent(std);
    // listResult.add(atd);
    // });
    // return listResult;
  }

}
