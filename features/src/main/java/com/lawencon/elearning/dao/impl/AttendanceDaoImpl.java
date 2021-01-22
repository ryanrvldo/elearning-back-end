package com.lawencon.elearning.dao.impl;

import org.springframework.stereotype.Repository;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.model.Attendance;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
@Repository
public class AttendanceDaoImpl extends BaseDaoImpl<Attendance> implements AttendanceDao {

  @Override
  public void createAttendance(Attendance data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public void verifAttendance(String id) throws Exception {
    String query = "UPDATE tb_r_attendances SET is_verified = true WHERE id = ?";
    createNativeQuery(query).setParameter(1, id).executeUpdate();
  }

}
