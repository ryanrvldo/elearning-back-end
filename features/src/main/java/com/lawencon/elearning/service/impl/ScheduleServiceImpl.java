package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ScheduleDao;
import com.lawencon.elearning.dto.ScheduleResponseDTO;
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
    if (data.getStartTime().isAfter(data.getEndTime())) {
      throw new IllegalRequestException("Schedule end time cannot be greather than start time");
    }
    if (scheduleDao.validateSchedule(data.getDate(), data.getStartTime(), data.getEndTime(),
        data.getTeacher().getId()) > 0) {
      throw new IllegalRequestException("Schedule already exist");
    }
    data.setCode(TransactionNumberUtils.generateScheduleCode());
    data.setCreatedAt(LocalDateTime.now());
    scheduleDao.saveSchedule(data, null);
  }

  @Override
  public void updateSchedule(Schedule data) throws Exception {
    validateNullId(data.getId(), "Id");
    scheduleDao.updateSchedule(data, null);
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
  public List<ScheduleResponseDTO> getByTeacherId(String teacherId) throws Exception {
    validateNullId(teacherId, "Teacher Id");

    List<Schedule> schedules = Optional.ofNullable(scheduleDao.getByTeacherId(teacherId))
        .orElseThrow(() -> new DataIsNotExistsException("Teacher Id", teacherId));

    List<ScheduleResponseDTO> listResult = new ArrayList<ScheduleResponseDTO>();

    schedules.forEach(schedule -> {
      ScheduleResponseDTO scheduleDTO = new ScheduleResponseDTO();
      scheduleDTO.setId(schedule.getId());
      scheduleDTO.setVersion(schedule.getVersion());
      scheduleDTO.setCode(schedule.getCode());
      scheduleDTO.setDate(schedule.getDate());
      scheduleDTO.setStartTime(schedule.getStartTime());
      scheduleDTO.setEndTime(schedule.getEndTime());
      listResult.add(scheduleDTO);
    });
    return listResult;
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
