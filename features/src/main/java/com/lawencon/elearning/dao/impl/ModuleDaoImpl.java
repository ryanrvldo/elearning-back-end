package com.lawencon.elearning.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.ModuleDao;
import com.lawencon.elearning.model.Module;
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
        "SELECT m.id as module_id, m.code, m.title, m.description, sc.subject_name, s.id as schedule_id, s.schedule_date, s.start_time, s.end_time ",
        "FROM tb_m_modules m ", "INNER JOIN tb_m_subject_categories sc ON sc.id = m.id_subject ",
        "INNER JOIN tb_m_schedules s ON s.id = m.id_schedule ", "WHERE m.id_course = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, idCourse).getResultList();
    return HibernateUtils.bMapperList(listObj, Module.class, "id", "code", "title", "description",
        "subject.subjectName", "schedule.id", "schedule.date", "schedule.startTime",
        "schedule.endTime");
  }

  @Override
  public void insertModule(Module data, Callback before) throws Exception {
    save(data, before, null, false, false);
  }

  @Override
  public void updateModule(Module data, Callback before) throws Exception {
    save(data, before, null, false, false);
  }

  @Override
  public void deleteModule(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    String query = "UPDATE from tb_m_modules SET is_active = false";
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
  public List<String> getLessonByIdModule(String idModule) throws Exception {
    String query = ("SELECT id_file, id_module FROM module_files WHERE id_module = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, idModule).getResultList();
    List<String> listIdFile = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      String idFile = (String) objArr[0];
      listIdFile.add(idFile);
    });
    return listIdFile;
  }

}
