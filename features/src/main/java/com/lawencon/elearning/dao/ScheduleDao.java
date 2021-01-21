package com.lawencon.elearning.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.util.Callback;

/**
 * @author Dzaky Fadhilla Guci
 */

public interface ScheduleDao {

  List<Schedule> getAllSchedules() throws Exception;

  void saveSchedule(Schedule data, Callback before) throws Exception;

  Schedule findScheduleById(String id) throws Exception;

  Schedule getByIdCustom(String id) throws Exception;

  List<Schedule> getByTeacherId(String id) throws Exception;

  Long checkScheduleTeacher(String id, LocalDate date, LocalTime startTime) throws Exception;


}
