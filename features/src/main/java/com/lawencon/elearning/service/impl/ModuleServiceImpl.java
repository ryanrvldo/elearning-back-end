package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ModuleDao;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.file.FileRequestDto;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.dto.module.ModuleListReponseDTO;
import com.lawencon.elearning.dto.module.ModuleRequestDTO;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.dto.module.UpdateModuleDTO;
import com.lawencon.elearning.dto.schedule.ScheduleResponseDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.AttendanceService;
import com.lawencon.elearning.service.CourseService;
import com.lawencon.elearning.service.FileService;
import com.lawencon.elearning.service.ModuleService;
import com.lawencon.elearning.service.ScheduleService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author WILLIAM
 */
@Service
public class ModuleServiceImpl extends BaseServiceImpl implements ModuleService {

  @Autowired
  private ModuleDao moduleDao;

  @Autowired
  private ScheduleService scheduleService;

  @Autowired
  private ValidationUtil validationUtil;

  @Autowired
  private FileService fileService;

  @Autowired
  private CourseService courseService;

  @Autowired
  private StudentService studentService;

  @Autowired
  private UserService userService;

  @Autowired
  private AttendanceService attendanceService;

  @Override
  public Module getModuleById(String id) throws Exception {
    validationUtil.validateUUID(id);
    return Optional.ofNullable(moduleDao.getModuleById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public List<ModuleResponseDTO> getModuleListByIdCourse(String idCourse, String idStudent)
      throws Exception {
    validationUtil.validateUUID(idCourse);
    Optional.ofNullable(courseService.getCourseById(idCourse))
        .orElseThrow(() -> new DataIsNotExistsException("course id", idCourse));
    if (idStudent == null || idStudent.trim().isEmpty()) {
      List<Module> listResult = moduleDao.getDetailCourse(idCourse);
      if (listResult.isEmpty()) {
        return Collections.emptyList();
      }
      List<ModuleResponseDTO> listModuleDTO = new ArrayList<>();
      for (int i = 0; i < listResult.size(); i++) {
        ModuleResponseDTO moduleDTO = new ModuleResponseDTO();
        moduleDTO.setId(listResult.get(i).getId());
        moduleDTO.setCode(listResult.get(i).getCode());
        moduleDTO.setTitle(listResult.get(i).getTitle());
        moduleDTO.setDescription(listResult.get(i).getDescription());
        moduleDTO.setSubjectName(listResult.get(i).getSubject().getSubjectName());

        ScheduleResponseDTO scheduleDTO = new ScheduleResponseDTO();
        scheduleDTO.setId(listResult.get(i).getSchedule().getId());
        scheduleDTO.setDate(listResult.get(i).getSchedule().getDate());
        scheduleDTO.setStartTime(listResult.get(i).getSchedule().getStartTime());
        scheduleDTO.setEndTime(listResult.get(i).getSchedule().getEndTime());
        moduleDTO.setSchedule(scheduleDTO);
        listModuleDTO.add(moduleDTO);
      }

      Comparator<ModuleResponseDTO> comparator =
          Comparator.comparing(module -> module.getSchedule().getDate());
      comparator = comparator
          .thenComparing(Comparator.comparing(module -> module.getSchedule().getStartTime()));
      listModuleDTO.sort(comparator);

      return listModuleDTO;
    } else {
      Optional.ofNullable(studentService.getStudentById(idStudent))
          .orElseThrow(() -> new DataIsNotExistsException("student id", idStudent));
      List<ModuleResponseDTO> listResult = moduleDao.getDetailCourseStudent(idCourse, idStudent);
      if (listResult.isEmpty()) {
        return Collections.emptyList();
      }
      for (ModuleResponseDTO moduleDTO : listResult) {
        attendanceService.getAttendanceForDetailCourse(idStudent, moduleDTO);;
      }
      Comparator<ModuleResponseDTO> comparator =
          Comparator.comparing(module -> module.getSchedule().getDate());
      comparator = comparator
          .thenComparing(Comparator.comparing(module -> module.getSchedule().getStartTime()));
      listResult.sort(comparator);

      return listResult;
    }
  }

  @Override
  public void insertModule(List<ModuleRequestDTO> data) throws Exception {
    for (ModuleRequestDTO moduleRequestDTO : data) {
      validationUtil.validate(moduleRequestDTO);
      validationUtil.validate(moduleRequestDTO.getSchedule());
      Course courseDb = Optional
          .ofNullable(courseService.getCourseById(moduleRequestDTO.getCourseId())).orElseThrow(
              () -> new DataIsNotExistsException("course id", moduleRequestDTO.getCourseId()));
      if (moduleRequestDTO.getSchedule().getDate()
          .isBefore(courseDb.getPeriodStart())
          || moduleRequestDTO.getSchedule().getDate()
          .isAfter(courseDb.getPeriodEnd())) {
        throw new IllegalRequestException("You can't insert module outside course period");
      }
      User userDb = userService.getById(moduleRequestDTO.getCreatedBy());
      if (!userDb.getRole().getCode().equalsIgnoreCase(Roles.ADMIN.getCode())) {
        throw new IllegalAccessException("You are unauthorized");
      }

      Schedule schedule = new Schedule();
      schedule.setCreatedBy(moduleRequestDTO.getSchedule().getCreatedBy());
      schedule.setDate(moduleRequestDTO.getSchedule().getDate());
      schedule.setEndTime(moduleRequestDTO.getSchedule().getEndTime());
      schedule.setStartTime(moduleRequestDTO.getSchedule().getStartTime());

      Teacher teacher = new Teacher();
      teacher.setId(courseDb.getTeacher().getId());
      schedule.setTeacher(teacher);

      Module module = new Module();
      module.setSchedule(schedule);
      module.setCode(moduleRequestDTO.getCode());
      module.setCreatedAt(LocalDateTime.now());
      module.setCreatedBy(moduleRequestDTO.getCreatedBy());
      module.setDescription(moduleRequestDTO.getDescription());
      module.setTitle(moduleRequestDTO.getTitle());

      Course course = new Course();
      course.setId(moduleRequestDTO.getCourseId());
      module.setCourse(course);

      SubjectCategory subject = new SubjectCategory();
      subject.setId(moduleRequestDTO.getSubjectId());
      module.setSubject(subject);

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
  public void updateModule(UpdateModuleDTO data) throws Exception {
    validationUtil.validate(data);
    validationUtil.validate(data.getSchedule());
    Module moduleDb = Optional.ofNullable(moduleDao.getModuleById(data.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("id module", data.getId()));
    Course courseDb = Optional.ofNullable(courseService.getCourseById(data.getCourseId()))
        .orElseThrow(() -> new DataIsNotExistsException("id course", data.getCourseId()));
    User userDb = Optional.ofNullable(userService.getById(data.getUpdatedBy()))
        .orElseThrow(() -> new DataIsNotExistsException("id user", data.getUpdatedBy()));

    if (data.getSchedule().getDate()
        .isBefore(courseDb.getPeriodStart())
        || data.getSchedule().getDate()
        .isAfter(courseDb.getPeriodEnd())) {
      throw new IllegalRequestException("You can't insert module outside course period");
    }

    if (!userDb.getRole().getCode().equalsIgnoreCase(Roles.ADMIN.getCode())) {
      throw new IllegalAccessException("You are unauthorized");
    }

    Schedule schedule = Optional
        .ofNullable(scheduleService.findScheduleById(data.getSchedule().getId()))
        .orElseThrow(() -> new DataIsNotExistsException("id schedule",
            data.getSchedule().getId()));
    schedule.setDate(data.getSchedule().getDate());
    schedule.setEndTime(data.getSchedule().getEndTime());
    schedule.setStartTime(data.getSchedule().getStartTime());
    schedule.setUpdatedBy(data.getUpdatedBy());

    Teacher teacher = new Teacher();
    teacher.setId(courseDb.getTeacher().getId());
    schedule.setTeacher(teacher);

    Module module = new Module();
    module.setSchedule(schedule);
    module.setCode(data.getCode());
    module.setTitle(data.getTitle());
    module.setDescription(data.getDescription());
    module.setId(data.getId());
    module.setUpdatedBy(data.getUpdatedBy());

    Course course = new Course();
    course.setId(data.getCourseId());
    module.setCourse(course);

    SubjectCategory subject = new SubjectCategory();
    subject.setId(data.getSubjectId());
    module.setSubject(subject);

    try {
      begin();
      scheduleService.updateSchedule(schedule);
      setupUpdatedValue(module, () -> moduleDb);
      moduleDao.updateModule(module, null);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

  @Override
  public void deleteModule(UpdateIsActiveRequestDTO data) throws Exception {
    validationUtil.validate(data);
    if (data.getStatus()) {
      throw new IllegalRequestException("Status must be false to delete data");
    }
    User userDb = userService.getById(data.getUpdatedBy());
    if (!userDb.getRole().getCode().equalsIgnoreCase(Roles.ADMIN.getCode())) {
      throw new IllegalAccessException("You are unauthorized");
    }
    Module moduleDb = Optional.ofNullable(moduleDao.getModuleById(data.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("id module", data.getId()));
    Schedule scheduleDb = scheduleService.findScheduleById(moduleDb.getSchedule().getId());
    try {
      begin();
      moduleDao.deleteModule(data.getId());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      updateIsActiveFalse(data.getId(), data.getUpdatedBy());
    }
    data.setId(scheduleDb.getId());
    try {
      begin();
      scheduleService.deleteSchedule(data.getId());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      scheduleService.updateIsActive(data);
    }
  }

  @Override
  public void updateIsActiveFalse(String id, String userId) throws Exception {
    try {
      begin();
      moduleDao.updateIsActiveFalse(id, userId);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public Module getModuleByIdCustom(String id) throws Exception {
    validationUtil.validateUUID(id);
    return Optional.ofNullable(moduleDao.getModuleByIdCustom(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public void saveLesson(MultipartFile multiPartFiles, String idUser, String idModule)
      throws Exception {
    validationUtil.validateUUID(idUser);
    validationUtil.validateUUID(idModule);

    if (multiPartFiles == null) {
      throw new IllegalRequestException("File cannot be empty!");
    }

    FileRequestDto fileRequest = new FileRequestDto();
    fileRequest.setType(FileType.MODULE_LESSON.toString());
    fileRequest.setUserId(idUser);
    try {
      begin();
      FileResponseDto fileResponseDTO = fileService.createFile(multiPartFiles, fileRequest);
      moduleDao.insertLesson(idModule, fileResponseDTO.getId());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public List<FileResponseDto> getLessonFile(String idModule) throws Exception {
    validationUtil.validateUUID(idModule);
    Optional.ofNullable(moduleDao.getModuleById(idModule))
        .orElseThrow(() -> new DataIsNotExistsException("id module", idModule));
    return moduleDao.getLessonByIdModule(idModule);
  }

  @Override
  public void updateIsActiveTrue(UpdateIsActiveRequestDTO data) throws Exception {
    try {
      begin();
      moduleDao.updateIsActiveTrue(data.getId(), data.getUpdatedBy());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public List<ModuleListReponseDTO> getModuleList(String idCourse) throws Exception {
    validationUtil.validateUUID(idCourse);
    Optional.ofNullable(courseService.getCourseById(idCourse))
        .orElseThrow(() -> new DataIsNotExistsException("course id", idCourse));
    List<Module> listResult = moduleDao.getModuleList(idCourse);
    if (listResult.isEmpty()) {
      return Collections.emptyList();
    }
    List<ModuleListReponseDTO> listModuleDTO = new ArrayList<>();
    for (int i = 0; i < listResult.size(); i++) {
      ModuleListReponseDTO moduleDTO = new ModuleListReponseDTO();
      moduleDTO.setId(listResult.get(i).getId());
      moduleDTO.setCode(listResult.get(i).getCode());
      moduleDTO.setTitle(listResult.get(i).getTitle());
      moduleDTO.setDescription(listResult.get(i).getDescription());
      moduleDTO.setSubjectName(listResult.get(i).getSubject().getSubjectName());
      moduleDTO.setIsActive(listResult.get(i).getIsActive());
      listModuleDTO.add(moduleDTO);
    }
    return listModuleDTO;
  }

  @Override
  public void deleteLesson(String fileId) throws Exception {
    validationUtil.validateUUID(fileId);
    if (moduleDao.checkLesson(fileId) == 0) {
      throw new DataIsNotExistsException("Data is not exits with file id : " + fileId + ".");
    }
    begin();
    moduleDao.deleteLesson(fileId);
    commit();
    fileService.deleteFile(fileId);
  }

}
