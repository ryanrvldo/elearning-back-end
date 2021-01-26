package com.lawencon.elearning.dao.impl;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.ScheduleDao;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.elearning.util.HibernateUtils;
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
  public void updateSchedule(Schedule data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public Schedule findScheduleById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public Schedule getByIdCustom(String id) throws Exception {
    String sql = buildQueryOf(
        "SELECT schedule_date , start_time , end_time  FROM tb_m_schedules tms ",
        "WHERE id = ?1 AND is_active = true").toString();

    List<Schedule> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    listResult =
        HibernateUtils.bMapperList(listObj, Schedule.class, "date", "startTime", "endTime");

    return getResultModel(listResult);
  }
  
  @Override

  public List<Schedule> getByTeacherId(String teacherId) throws Exception {
    String sql = buildQueryOf("SELECT code, schedule_date, start_time, end_time ",
        "  FROM tb_m_schedules tms WHERE id_teacher=?1  AND is_active = true").toString();

    List<Schedule> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, teacherId).getResultList();

    listResult =
        HibernateUtils.bMapperList(listObj, Schedule.class, "code", "date", "startTime", "endTime");

    return listResult.size() > 0 ? listResult : null;
  }

  @Override
  public Long checkScheduleTeacher(String id, LocalDate date, LocalTime startTime)
      throws Exception {
    String sql = buildQueryOf(
        "SELECT count(*) FROM tb_m_schedules WHERE id_teacher = ?1 AND schedule_date = ?2 AND start_time = ?3  AND is_active = true")
        .toString();

    return (Long) createNativeQuery(sql).setParameter(1, id).setParameter(2, date)
        .setParameter(3, startTime).getSingleResult();
  }



}
