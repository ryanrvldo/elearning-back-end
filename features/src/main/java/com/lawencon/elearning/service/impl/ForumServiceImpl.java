package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ForumDao;
import com.lawencon.elearning.dto.forum.ForumModuleResponseDTO;
import com.lawencon.elearning.dto.forum.ForumRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Forum;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.ForumService;
import com.lawencon.elearning.util.TransactionNumberUtils;
import com.lawencon.elearning.util.ValidationUtil;

/**
 *  @author Dzaky Fadhilla Guci
 */
@Service
public class ForumServiceImpl extends BaseServiceImpl implements ForumService {

  @Autowired
  private ForumDao forumDao;

  @Autowired
  private ValidationUtil validateUtil;

  @Override
  public List<Forum> getAllForums() throws Exception {
    return Optional.ofNullable(forumDao.getAllForums())
        .orElseThrow(
            () -> new DataIsNotExistsException("Forum is empty and has not been initialized."));
  }

  @Override
  public void saveForum(ForumRequestDTO data) throws Exception {
    validateUtil.validate(data);

    User user = new User();
    user.setId(data.getUserId());
    user.setVersion(data.getVersionUser());

    Module module = new Module();
    module.setId(data.getModuleId());
    module.setVersion(data.getVersionModule());
    Forum forum = new Forum();
    forum.setContent(data.getContent());
    forum.setUser(user);
    forum.setModule(module);

    forum.setCreatedAt(LocalDateTime.now());
    forum.setCreatedBy(data.getUserId());
    forum.setTrxDate(LocalDate.now());

    forum.setTrxNumber(TransactionNumberUtils.generateForumTrxNumber());
    forumDao.saveForum(forum, null);
  }

  @Override
  public void updateForum(Forum data) throws Exception {
    validateNullId(data.getId(), "id");
    forumDao.saveForum(data, null);
  }

  @Override
  public Forum findForumById(String id) throws Exception {
    validateNullId(id, "id");
    return Optional.ofNullable(forumDao.findForumById(id)).orElseThrow(
        () -> new DataIsNotExistsException("Discussion in this module has not been initialized."));
  }

  @Override
  public List<ForumModuleResponseDTO> getByModuleId(String moduleId) throws Exception {
    validateNullId(moduleId, "Id Module");
    List<Forum> forums = forumDao.getByModuleId(moduleId);
    List<ForumModuleResponseDTO> forumResponses = new ArrayList<>();

    forums.forEach(val -> {
      ForumModuleResponseDTO forumDTO = new ForumModuleResponseDTO(val.getId(), val.getTrxNumber(),
          val.getContent(), val.getCreatedAt(), val.getUser().getId(), val.getUser().getFirstName(),
          val.getUser().getLastName(), val.getUser().getRole().getCode(),
          val.getUser().getUserPhoto().getId());
      forumResponses.add(forumDTO);
    });

    return forumResponses;
  }

  @Override
  public void deleteForum(String id, String userId) throws Exception {
    validateNullId(id, "Id");
    if (forumDao.checkByForumIdAndUserId(id, userId) > 0) {
      begin();
      forumDao.deleteForum(id);
      commit();
    } else {
      throw new IllegalRequestException("User Id", userId);
    }

  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
