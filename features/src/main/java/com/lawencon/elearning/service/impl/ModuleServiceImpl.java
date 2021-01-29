package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ModuleDao;
import com.lawencon.elearning.dto.module.ModulRequestDTO;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.service.ModuleService;
import com.lawencon.elearning.service.ScheduleService;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * 
 * @author WILLIAM
 *
 */
@Service
public class ModuleServiceImpl extends BaseServiceImpl implements ModuleService {

  @Autowired
  private ModuleDao moduleDao;

  @Autowired
  private ScheduleService scheduleService;

  @Autowired
  private ValidationUtil validationUtil;

  @Override
  public Module getModuleById(String id) throws Exception {
    validateNullId(id, "id");
    return Optional.ofNullable(moduleDao.getModuleById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public List<ModuleResponseDTO> getDetailCourse(String idCourse) throws Exception {
    List<Module> listResult = moduleDao.getDetailCourse(idCourse);
    if (listResult.isEmpty()) {
      throw new DataIsNotExistsException("There is no module yet");
    }
    List<ModuleResponseDTO> listModuleDTO = new ArrayList<>();
    for (int i = 0; i < listResult.size(); i++) {
      ModuleResponseDTO moduleDTO = new ModuleResponseDTO();
      moduleDTO.setId(listResult.get(i).getId());
      moduleDTO.setCode(listResult.get(i).getCode());
      moduleDTO.setTittle(listResult.get(i).getTitle());
      moduleDTO.setDescription(listResult.get(i).getDescription());
      moduleDTO.setSubjectName(listResult.get(i).getSubject().getSubjectName());
      moduleDTO.setIdSchedule(listResult.get(i).getSchedule().getId());
      moduleDTO.setScheduleDate(listResult.get(i).getSchedule().getDate());
      moduleDTO.setStartTime((listResult.get(i).getSchedule().getStartTime()));
      moduleDTO.setEndTime((listResult.get(i).getSchedule().getEndTime()));
      listModuleDTO.add(moduleDTO);
    }
    return listModuleDTO;
  }

  @Override
  public void insertModule(List<ModulRequestDTO> data) throws Exception {
    for (int i = 0; i < data.size(); i++) {
      validationUtil.validate(data.get(i));
      Schedule schedule = new Schedule();
      schedule.setCreatedBy(data.get(i).getScheduleRequestDTO().getScheduleCreatedBy());
      schedule.setDate(data.get(i).getScheduleRequestDTO().getScheduleDate());
      schedule.setEndTime(data.get(i).getScheduleRequestDTO().getScheduleEnd());
      schedule.setStartTime(data.get(i).getScheduleRequestDTO().getScheduleStart());
      Teacher teacher = new Teacher();
      teacher.setId(data.get(i).getScheduleRequestDTO().getTeacherId());
      teacher.setVersion(data.get(i).getScheduleRequestDTO().getTeacherVersion());
      schedule.setTeacher(teacher);
      Module module = new Module();
      module.setSchedule(schedule);
      Course course = new Course();
      course.setId(data.get(i).getCourseId());
      course.setVersion(data.get(i).getCourseVersion());
      module.setCourse(course);
      SubjectCategory subject = new SubjectCategory();
      subject.setId(data.get(i).getSubjectId());
      subject.setVersion(data.get(i).getSubjectVersion());
      module.setSubject(subject);
      module.setCode(data.get(i).getModuleCode());
      module.setCreatedAt(LocalDateTime.now());
      module.setCreatedBy(data.get(i).getModuleCreatedBy());
      module.setDescription(data.get(i).getModuleDescription());
      module.setTitle(data.get(i).getModuleTittle());
      try {
        begin();
        scheduleService.saveSchedule(schedule);
        moduleDao.insertModule(module, null);
        commit();
      } catch (Exception e) {
        e.printStackTrace();
        rollback();
        throw e;
      }
    }
  }

  @Override
  public void updateModule(Module data) throws Exception {
    validateNullId(data.getId(), "id");
    setupUpdatedValue(data, () -> moduleDao.getModuleById(data.getId()));
    moduleDao.updateModule(data, null);
  }

  @Override
  public void deleteModule(String id) throws Exception {
    validateNullId(id, "id");
    begin();
    moduleDao.deleteModule(id);
    commit();
  }

  @Override
  public Module getModuleByIdCustom(String id) throws Exception {
    validateNullId(id, "id");
    return Optional.ofNullable(moduleDao.getModuleByIdCustom(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
