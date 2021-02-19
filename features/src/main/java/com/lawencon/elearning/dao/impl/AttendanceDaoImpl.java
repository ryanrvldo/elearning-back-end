package com.lawencon.elearning.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.AttendanceDao;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
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
  public void verifyAttendance(Attendance data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public List<Attendance> getAttendanceList(String idModule) throws Exception {
    String query = buildQueryOf(
        "SELECT DISTINCT COALESCE(id, '0') AS attendance_id, COALESCE(created_at, '0001-01-01 00:00'), ",
        "COALESCE(VERSION, 0) as att_version, COALESCE(is_verified, FALSE) as att_verify, ",
        "COALESCE(id_student, '0') AS student_id ", "FROM tb_r_attendances ",
        "WHERE id_module = ? ");
    List<?> listObj = createNativeQuery(query).setParameter(1, idModule).getResultList();
    return HibernateUtils.bMapperList(listObj, Attendance.class, "id", "createdAt", "version",
        "isVerified", "student.id");
  }

  @Override
  public Attendance getAttendanceById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public Attendance checkAttendanceStatus(String idModule, String idStudent) throws Exception {
    String sql = buildQueryOf("SELECT a.is_verified, a.id ", "FROM tb_m_students s ",
        "INNER JOIN tb_r_attendances a ON a.id_student = s.id ",
        "WHERE s.id = ? AND a.id_module = ?");
    List<?> listObj =
        createNativeQuery(sql).setParameter(1, idStudent).setParameter(2, idModule).getResultList();
    List<Attendance> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Attendance att = new Attendance();
      att.setIsVerified((boolean) objArr[0]);
      att.setId((String) objArr[1]);
      listResult.add(att);
    });
    return getResultModel(listResult);
  }

  @Override
  public void getAttendanceForDetailCourse(String studentId, ModuleResponseDTO data) throws Exception {
    String sql = buildQueryOf("SELECT a.id, COALESCE(a.is_verified, FALSE) ",
        "FROM tb_r_attendances a ", "WHERE a.id_student = ?1 ", "AND a.id_module = ?2");
    List<?> listObj = createNativeQuery(sql).setParameter(1, studentId)
        .setParameter(2, data.getId()).getResultList();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      data.setAttendanceId((String) objArr[0]);
      data.setVerifyStatus((Boolean) objArr[1]);
    });
  }

}