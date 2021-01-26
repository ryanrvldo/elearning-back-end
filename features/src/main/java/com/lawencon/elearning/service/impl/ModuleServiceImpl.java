package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ModuleDao;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.service.ModuleService;
import com.lawencon.elearning.service.ScheduleService;

/**
 * 
 * @author WILLIAM
 *
 */
@Service
public class ModuleServiceImpl extends BaseServiceImpl implements ModuleService {

  @Autowired
  ModuleDao moduleDao;

  @Autowired
  ScheduleService scheduleService;

  @Override
  public Module getModuleById(String id) throws Exception {
    return moduleDao.getModuleById(id);
  }

  @Override
  public List<Module> getDetailCourse(String idCourse) throws Exception {
    return moduleDao.getDetailCourse(idCourse);
  }

  @Override
  public void insertModule(List<Module> data) throws Exception {
    for (int i = 0; i < data.size(); i++) {
      scheduleService.saveSchedule(data.get(0).getSchedule());
      data.get(i).setCreatedAt(LocalDateTime.now());
      moduleDao.insertModule(data.get(i), null);
    }
  }

  @Override
  public void updateModule(Module data) throws Exception {
    setupUpdatedValue(data, () -> moduleDao.getModuleById(data.getId()));
    moduleDao.updateModule(data, null);
  }

  @Override
  public void deleteModule(String id) throws Exception {
    begin();
    moduleDao.deleteModule(id);
    commit();
  }

  @Override
  public Module getModuleByIdCustom(String id) throws Exception {
    return moduleDao.getModuleByIdCustom(id);
  }

}
