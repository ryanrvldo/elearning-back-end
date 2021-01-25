package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ForumDao;
import com.lawencon.elearning.model.Forum;
import com.lawencon.elearning.service.ForumService;

/**
 *  @author Dzaky Fadhilla Guci
 */
@Service
public class ForumServiceImpl extends BaseServiceImpl implements ForumService {

  @Autowired
  private ForumDao forumDao;

  @Override
  public List<Forum> getAllForums() throws Exception {
    return forumDao.getAllForums();
  }

  @Override
  public void saveForum(Forum data) throws Exception {
    LocalDateTime timeNow = LocalDateTime.now();
    data.setCreatedAt(timeNow);
    data.setTrxDate(LocalDate.now());
    StringBuilder sb = new StringBuilder("FRM-");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
    String formattedDateTime = timeNow.format(formatter);
    sb.append(formattedDateTime);

    forumDao.saveForum(data, null);
  }

  @Override
  public void updateForum(Forum data) throws Exception {
    forumDao.saveForum(data, null);
  }

  @Override
  public Forum findForumById(String id) throws Exception {
    return forumDao.findForumById(id);
  }

  @Override
  public List<Forum> getByModuleId(String moduleId) throws Exception {
    return forumDao.getByModuleId(moduleId);
  }

}
