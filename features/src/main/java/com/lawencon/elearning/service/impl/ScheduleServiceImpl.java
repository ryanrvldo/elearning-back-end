package com.lawencon.elearning.service.impl;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ScheduleDao;
import com.lawencon.elearning.dto.ScheduleResponseDTO;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.service.ScheduleService;
import com.lawencon.elearning.service.TeacherService;
import com.lawencon.elearning.util.TransactionNumberUtils;
import com.lawencon.elearning.util.ValidationUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dzaky Fadhilla Guci
 */

@Service
public class ScheduleServiceImpl extends BaseServiceImpl implements ScheduleService {

  @Autowired
  private ScheduleDao scheduleDao;

  @Autowired
  private TeacherService teacherService;

  @Autowired
  private ValidationUtil validateUtil;

  @Override
  public List<Schedule> getAllSchedules() throws Exception {
    return Optional.ofNullable(scheduleDao.getAllSchedules())
        .orElse(Collections.emptyList());
  }

  @Override
  public void saveSchedule(Schedule data) throws Exception {
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
    validateUtil.validateUUID(data.getId());
    if (data.getStartTime().isAfter(data.getEndTime())) {
      throw new IllegalRequestException("Schedule end time cannot be greather than start time");
    }
    if (scheduleDao.validateSchedule(data.getDate(), data.getStartTime(), data.getEndTime(),
        data.getTeacher().getId()) > 0) {
      throw new IllegalRequestException("Schedule already exist");
    }
    scheduleDao.updateSchedule(data, null);
  }

  @Override
  public Schedule findScheduleById(String id) throws Exception {
    validateUtil.validateUUID(id);
    return Optional.ofNullable(scheduleDao.findScheduleById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public Schedule getByIdCustom(String id) throws Exception {
    validateUtil.validateUUID(id);
    return Optional.ofNullable(scheduleDao.getByIdCustom(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));

  }

  @Override
  public List<ScheduleResponseDTO> getByTeacherId(String teacherId) throws Exception {
    validateUtil.validateUUID(teacherId);
    Teacher teacher = teacherService.findTeacherById(teacherId);
    List<Schedule> schedules = scheduleDao.getByTeacherId(teacher.getId());
    if (schedules.isEmpty()) {
      return Collections.emptyList();
    }

    List<ScheduleResponseDTO> listResult = new ArrayList<>();
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
  public void deleteSchedule(String id) throws Exception {
    validateUtil.validateUUID(id);
    try {
      begin();
      scheduleDao.deleteSchedule(id);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

  @Override
  public void updateIsActive(UpdateIsActiveRequestDTO request) throws Exception {
    validateUtil.validate(request);
    try {
      begin();
      scheduleDao.updateIsActive(request);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

}
