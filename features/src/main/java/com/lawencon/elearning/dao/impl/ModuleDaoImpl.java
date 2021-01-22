package com.lawencon.elearning.dao.impl;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.ModuleDao;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
        "INNER JOIN tb_m_schedules s on s.id = m.id_schedule " + "where m.id = ?").toString();
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
      schedule.setDate((LocalDate) objArr[8]);
      schedule.setStartTime((LocalTime) objArr[9]);
      schedule.setEndTime((LocalTime) objArr[10]);
      module.setSchedule(schedule);
      listResult.add(module);
    });
    return listResult.get(0);

  }

}
