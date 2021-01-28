package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ScheduleDao;
import com.lawencon.elearning.error.DataAlreadyExistException;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.elearning.service.ScheduleService;
import com.lawencon.elearning.util.TransactionNumberUtils;
import com.lawencon.elearning.util.ValidationUtil;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Service
public class ScheduleServiceImpl extends BaseServiceImpl implements ScheduleService {

  @Autowired
  private ScheduleDao scheduleDao;

  @Autowired
  private ValidationUtil validateUtil;

  @Override
  public List<Schedule> getAllSchedules() throws Exception {
    return Optional.ofNullable(scheduleDao.getAllSchedules()).orElseThrow(
        () -> new DataIsNotExistsException("Schedule is empty and has not been initialized."));
  }

  @Override
  public void saveSchedule(Schedule data) throws Exception {
    validateUtil.validate(data);
    if (checkScheduleTeacher(data.getTeacher().getId(), data.getDate(), data.getStartTime()) > 0) {
      throw new DataAlreadyExistException("Time", data.getDate().toString());
    }
    data.setCode(TransactionNumberUtils.generateScheduleCode());
    data.setCreatedAt(LocalDateTime.now());
    scheduleDao.saveSchedule(data, () -> new DataAlreadyExistException(""));
  }

  @Override
  public void updateSchedule(Schedule data) throws Exception {
    validateNullId(data.getId(), "Id");
    scheduleDao.saveSchedule(data, null);
  }

  @Override
  public Schedule findScheduleById(String id) throws Exception {
    validateNullId(id, "Id");
    return Optional.ofNullable(scheduleDao.findScheduleById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public Schedule getByIdCustom(String id) throws Exception {
    validateNullId(id, "Id");
    return Optional.ofNullable(scheduleDao.getByIdCustom(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));

  }

  @Override
  public List<Schedule> getByTeacherId(String teacherId) throws Exception {
    validateNullId(teacherId, "Teacher Id");
    return Optional.ofNullable(scheduleDao.getByTeacherId(teacherId))
        .orElseThrow(() -> new DataIsNotExistsException("Teacher Id", teacherId));
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
