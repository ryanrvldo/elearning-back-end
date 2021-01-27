package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ScheduleDao;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.elearning.service.ScheduleService;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Service
public class ScheduleServiceImpl extends BaseServiceImpl implements ScheduleService {

  @Autowired
  private ScheduleDao scheduleDao;

  @Override
  public List<Schedule> getAllSchedules() throws Exception {
    return scheduleDao.getAllSchedules();
  }

  @Override
  public void saveSchedule(Schedule data) throws Exception {
    if (checkScheduleTeacher(data.getTeacher().getId(), data.getDate(), data.getStartTime()) > 0) {
      throw new Exception("Teacher already has schedule on that time");
    }
      data.setCreatedAt(LocalDateTime.now());
      scheduleDao.saveSchedule(data, null);
  }

  @Override
  public void updateSchedule(Schedule data) throws Exception {
    validateNullId(data.getId(), "Id");
    scheduleDao.saveSchedule(data, null);
  }

  @Override
  public Schedule findScheduleById(String id) throws Exception {
    validateNullId(id, "Id");
    return scheduleDao.findScheduleById(id);
  }

  @Override
  public Schedule getByIdCustom(String id) throws Exception {
    validateNullId(id, "Id");
    return scheduleDao.getByIdCustom(id);

  }

  @Override
  public List<Schedule> getByTeacherId(String teacherId) throws Exception {
    validateNullId(teacherId, "Teacher Id");
    return scheduleDao.getByTeacherId(teacherId);
  }

  @Override
  public Long checkScheduleTeacher(String teacherId, LocalDate date, LocalTime startTime)
      throws Exception {
    validateNullId(teacherId, "Teacher Id");
    return scheduleDao.checkScheduleTeacher(teacherId, date, startTime);
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }



}
