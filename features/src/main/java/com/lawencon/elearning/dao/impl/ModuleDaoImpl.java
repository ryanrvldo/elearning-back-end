package com.lawencon.elearning.dao.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.ModuleDao;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.elearning.model.SubjectCategory;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
public class ModuleDaoImpl extends BaseDaoImpl<Module> implements ModuleDao, BaseCustomDao {

  @Override
  public Module getById(String id) {
    List<Module> listResult = new ArrayList<>();
    String query = bBuilder(
        "SELECT m.title, m.code, m.description, f.id, f.data, f.name, f.extension, f.size, f.type, ",
        "s.schedule_date, s.start_time, s.end_time " + "FROM tb_m_modules m ",
        "INNER JOIN tb_r_files f ON f.id = m.id_file ",
        "INNER JOIN tb_m_schedules s on s.id = m.id_schedule where m.id = ?").toString();
    List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Module module = new Module();
      module.setTitle((String) objArr[0]);
      module.setCode((String) objArr[1]);
      module.setDescription((String) objArr[2]);
      File file = new File();
      file.setId((String) objArr[3]);
      file.setData((byte[]) objArr[4]);
      file.setName((String) objArr[5]);
      file.setExtension((String) objArr[5]);
      file.setSize((Long) objArr[6]);
      file.setType((FileType) objArr[7]);
      // TODO: 1/22/21 FIX THIS
      module.setFiles(null);
      Schedule schedule = new Schedule();
      Date inDate = (Date) objArr[8];
      schedule.setDate((LocalDate) inDate.toLocalDate());
      Time inTime = (Time) objArr[9];
      schedule.setStartTime((LocalTime) inTime.toLocalTime());
      inTime = (Time) objArr[10];
      schedule.setEndTime((LocalTime) inTime.toLocalTime());
      module.setSchedule(schedule);
      listResult.add(module);
    });
    return listResult.get(0);

  }

  @Override
  public List<Module> getDetailCourse(String id) {
    String query = bBuilder(
        "SELECT m.id, m.code, m.title, m.description, sc.subject_name, s.id, s.schedule_date, s.start_time, s.end_time ",
        "FROM tb_m_modules m ", "INNER JOIN tb_m_subject_categories sc ON sc.id = m.id_subject ",
        "INNER JOIN tb_m_schedules s ON s.id = m.id_schedule ", "WHERE m.id_course = ?").toString();
    List<Module> listResult = new ArrayList<>();
    List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Module mdl = new Module();
      mdl.setId((String) objArr[0]);
      mdl.setCode((String) objArr[1]);
      mdl.setTitle((String) objArr[2]);
      mdl.setDescription((String) objArr[3]);
      SubjectCategory subCat = new SubjectCategory();
      subCat.setSubjectName((String) objArr[4]);
      mdl.setSubject(subCat);
      Schedule schdl = new Schedule();
      schdl.setId((String) objArr[5]);
      Date inDate = (Date) objArr[6];
      schdl.setDate((LocalDate) inDate.toLocalDate());
      Time inTime = (Time) objArr[7];
      schdl.setStartTime((LocalTime) inTime.toLocalTime());
      inTime = (Time) objArr[8];
      schdl.setEndTime((LocalTime) inTime.toLocalTime());
      mdl.setSchedule(schdl);
      listResult.add(mdl);
    });
    return listResult;
  }

  @Override
  public void insertModule(Module data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public void updateModule(Module data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public void deleteModule(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public void softDeleteModule(String id) throws Exception {
    String query = "UPDATE from tb_m_modules SET is_active = false WHERE id = ?";
    createNativeQuery(query).setParameter(1, id).executeUpdate();
  }

}
