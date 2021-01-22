package com.lawencon.elearning.dao.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.ScheduleDao;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.util.Callback;

/**
 * @author Dzaky Fadhilla Guci
 */

@Repository
public class ScheduleDaoImpl extends CustomBaseDao<Schedule> implements ScheduleDao {

  @Override
  public List<Schedule> getAllSchedules() throws Exception {
    return getAll();
  }

  @Override
  public void saveSchedule(Schedule data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public Schedule findScheduleById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public Schedule getByIdCustom(String id) throws Exception {
    String sql = bBuilder("SELECT schedule_date , start_time , end_time  FROM tb_m_schedules tms ",
        "WHERE id = ?1").toString();

    List<Schedule> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Schedule s = new Schedule();
      s.setDate((LocalDate) objArr[0]);
      s.setStartTime((LocalTime) objArr[1]);
      s.setEndTime((LocalTime) objArr[3]);

      listResult.add(s);
    });
    return getResultModel(listResult);
  }
  
  @Override
  public List<Schedule> getByTeacherId(String id) throws Exception {
    String sql = bBuilder("SELECT code, schedule_date, start_time, end_time ",
        "  FROM tb_m_schedules tms WHERE id_teacher=?").toString();

    List<Schedule> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Schedule s = new Schedule();
      s.setCode((String) objArr[0]);

      Date inDate = (Date) objArr[1];
      s.setDate((LocalDate) inDate.toLocalDate());

      Time inTime = (Time) objArr[2];
      s.setStartTime((LocalTime) inTime.toLocalTime());

      inTime = (Time) objArr[3];
      s.setEndTime((LocalTime) inTime.toLocalTime());

      listResult.add(s);
    });
    return listResult;
  }

  @Override
  public Long checkScheduleTeacher(String id, LocalDate date, LocalTime startTime)
      throws Exception {
    String sql = bBuilder(
        "SELECT count(*) FROM tb_m_schedules tms WHERE id_teacher = ?1 AND schedule_date = ?2 AND start_time = ?3")
            .toString();

    return (Long) createNativeQuery(sql).setParameter(1, id).setParameter(2, date)
        .setParameter(3, startTime).getSingleResult();
  }



}
