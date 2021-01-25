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
  public void verifAttendance(Attendance data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public List<Attendance> getAttendanceList(String idModule) throws Exception {
    String query = buildQueryOf("SELECT a.created_at, a.is_verified, s.first_name, s.last_name ",
        "FROM tb_r_attendances a ", "INNER JOIN tb_m_students s ON s.id = a.id_student ",
        "WHERE a.id_module = ?").toString();
    List<?> listObj = createNativeQuery(query).setParameter(1, idModule).getResultList();
    return HibernateUtils.bMapperList(listObj, Attendance.class, "createdAt", "isVerified",
        "student.firstName", "student.lastName");
  }

}
