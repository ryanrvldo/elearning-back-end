package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ForumDao;
import com.lawencon.elearning.dto.ForumModuleResponseDTO;
import com.lawencon.elearning.error.IllegalRequestException;
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
    data.setCreatedBy(data.getUser().getId());
    data.setTrxDate(LocalDate.now());

    String trxNumber = generateTrxNumber(timeNow);
    data.setTrxNumber(trxNumber);
    forumDao.saveForum(data, null);
  }

  private String generateTrxNumber(LocalDateTime timeNow) {
    StringBuilder sb = new StringBuilder("FRM");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy-HHmmss");
    String formattedDateTime = timeNow.format(formatter);
    sb.append(formattedDateTime);
    return sb.toString();
  }

  @Override
  public void updateForum(Forum data) throws Exception {
    validateNullId(data.getId(), "id");
    forumDao.saveForum(data, null);
  }

  @Override
  public Forum findForumById(String id) throws Exception {
    validateNullId(id, "id");
    return forumDao.findForumById(id);
  }

  @Override
  public List<ForumModuleResponseDTO> getByModuleId(String moduleId) throws Exception {
    validateNullId(moduleId, "Id Module");
    List<Forum> forums = forumDao.getByModuleId(moduleId);
    List<ForumModuleResponseDTO> forumResponses = new ArrayList<>();

    forums.forEach(val -> {
      ForumModuleResponseDTO forumDTO = new ForumModuleResponseDTO(val.getId(), val.getTrxNumber(),
          val.getContent(), val.getCreatedAt(), val.getUser());
      forumResponses.add(forumDTO);
    });

    return forumResponses;
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
