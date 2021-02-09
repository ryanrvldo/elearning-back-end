package com.lawencon.elearning.dao.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.ModuleDao;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import com.lawencon.elearning.dto.schedule.ScheduleResponseDTO;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
@Repository
public class ModuleDaoImpl extends CustomBaseDao<Module> implements ModuleDao {

  @Override
  public Module getModuleById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public List<Module> getDetailCourse(String idCourse) throws Exception {
    String query = buildQueryOf(
        "SELECT m.id as module_id, m.code, m.title, m.description, sc.subject_name, s.id as schedule_id, ",
        "s.schedule_date, s.start_time, s.end_time ",
        "FROM tb_m_modules m ", "INNER JOIN tb_m_subject_categories sc ON sc.id = m.id_subject ",
        "INNER JOIN tb_m_schedules s ON s.id = m.id_schedule ", "WHERE m.id_course = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, idCourse).getResultList();
    List<Module> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Module module = new Module();
      module.setId((String) objArr[0]);
      module.setCode((String) objArr[1]);
      module.setTitle((String) objArr[2]);
      module.setDescription((String) objArr[3]);

      SubjectCategory subject = new SubjectCategory();
      subject.setSubjectName((String) objArr[4]);
      module.setSubject(subject);

      Schedule schedule = new Schedule();
      schedule.setId((String) objArr[5]);
      Date inDate = (Date) objArr[6];
      schedule.setDate(inDate.toLocalDate());
      Time inTime = (Time) objArr[7];
      schedule.setStartTime(inTime.toLocalTime());
      inTime = (Time) objArr[8];
      schedule.setEndTime(inTime.toLocalTime());
      module.setSchedule(schedule);
      listResult.add(module);
    });
    return listResult;
  }

  @Override
  public List<ModuleResponseDTO> getDetailCourseStudent(String idCourse, String idStudent)
      throws Exception {
    String query = buildQueryOf(
        "SELECT DISTINCT ON(m.id) m.id as module_id, m.code, m.title, m.description, sc.subject_name, ",
        "s.id as schedule_id, ",
        "s.schedule_date, s.start_time, s.end_time, a.id as attendance_id, COALESCE(a.is_verified, FALSE) ",
        "FROM tb_m_modules m ",
        "INNER JOIN tb_m_subject_categories sc ON sc.id = m.id_subject ",
        "INNER JOIN tb_m_schedules s ON s.id = m.id_schedule ",
        "INNER JOIN student_course sco ON sco.id_course = m.id_course ",
        "INNER JOIN tb_m_students std ON std.id = sco.id_student ",
        "LEFT JOIN tb_r_attendances a ON a.id_module = m.id ", "WHERE m.id_course = ? ",
        "AND std.id = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, idCourse).setParameter(2, idStudent)
        .getResultList();
    List<ModuleResponseDTO> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      ModuleResponseDTO module = new ModuleResponseDTO();
      module.setId((String) objArr[0]);
      module.setCode((String) objArr[1]);
      module.setTittle((String) objArr[2]);
      module.setDescription((String) objArr[3]);
      module.setSubjectName((String) objArr[4]);
      module.setAttendanceId((String) objArr[9]);
      module.setVerifyStatus((boolean) objArr[10]);

      ScheduleResponseDTO schedule = new ScheduleResponseDTO();
      schedule.setId((String) objArr[5]);
      Date inDate = (Date) objArr[6];
      schedule.setDate(inDate.toLocalDate());
      Time inTime = (Time) objArr[7];
      schedule.setStartTime(inTime.toLocalTime());
      inTime = (Time) objArr[8];
      schedule.setEndTime(inTime.toLocalTime());
      module.setSchedule(schedule);
      listResult.add(module);
    });
    return listResult;
  }

  @Override
  public void insertModule(Module data, Callback before) throws Exception {
    save(data, before, null, false, false);
  }

  @Override
  public void updateModule(Module data, Callback before) throws Exception {
    save(data, before, null, false, true);
  }

  @Override
  public void deleteModule(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public void updateIsActiveFalse(String id, String userId) throws Exception {
    String query = "UPDATE tb_m_modules SET is_active = false";
    updateNativeSQL(query, id, userId);
  }

  @Override
  public Module getModuleByIdCustom(String id) throws Exception {
    String query = buildQueryOf(
        "SELECT m.title, m.code, m.description, s.schedule_date, s.start_time, s.end_time ",
        "FROM tb_m_modules as m ", "INNER JOIN tb_m_schedules as s on s.id = m.id_schedule ",
        "WHERE m.id = ?");
    List<?> listObj =
        createNativeQuery(query).setParameter(1, id).getResultList();
    List<Module> listResult = HibernateUtils.bMapperList(listObj, Module.class, "title", "code",
        "description", "schedule.date", "schedule.startTime", "schedule.endTime");
    return getResultModel(listResult);
  }

  @Override
  public void insertLesson(String idModule, String idFile) throws Exception {
    createNativeQuery("INSERT INTO module_files VALUES (?1, ?2)").setParameter(1, idModule)
        .setParameter(2, idFile).executeUpdate();
  }

  @Override
  public List<FileResponseDto> getLessonByIdModule(String idModule) throws Exception {
    String query = buildQueryOf("SELECT f.id, f.name, f.type, f.size, f.content_type, f.version ",
        "FROM module_files mf ", "INNER JOIN tb_r_files f ON f.id = mf.id_file ",
        "WHERE id_module = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, idModule).getResultList();
    List<FileResponseDto> listFileDto = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      FileResponseDto fileDto = new FileResponseDto();
      fileDto.setId((String) objArr[0]);
      fileDto.setFileName((String) objArr[1]);
      fileDto.setFileType(FileType.valueOf(String.valueOf(objArr[2])));
      fileDto.setSize(Long.valueOf(objArr[3].toString()));
      fileDto.setContentType((String) objArr[4]);
      fileDto.setVersion(Long.valueOf(objArr[5].toString()));
      listFileDto.add(fileDto);
    });
    return listFileDto;
  }

  public List<Module> getListModule() throws Exception {
    return getAll();
  }

  @Override
  public void updateIsActiveTrue(String id, String userId) throws Exception {
    String query = "UPDATE tb_m_modules SET is_active = true";
    updateNativeSQL(query, id, userId);
  }

}
