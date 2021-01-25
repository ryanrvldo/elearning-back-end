package com.lawencon.elearning.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.lawencon.elearning.model.Schedule;

/**
 *  @author Dzaky Fadhilla Guci
 */

public interface ScheduleService {

  List<Schedule> getAllSchedules() throws Exception;

  void saveSchedule(Schedule data) throws Exception;

  void updateSchedule(Schedule data) throws Exception;

  Schedule findScheduleById(String id) throws Exception;

  Schedule getByIdCustom(String id) throws Exception;

  List<Schedule> getByTeacherId(String teacherId) throws Exception;

  Long checkScheduleTeacher(String teacherId, LocalDate date, LocalTime startTime) throws Exception;

}
