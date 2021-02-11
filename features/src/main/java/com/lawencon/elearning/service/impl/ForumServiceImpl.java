package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
 * @author Dzaky Fadhilla Guci
 */
@Service
public class ForumServiceImpl extends BaseServiceImpl implements ForumService {

  @Autowired
  private ForumDao forumDao;

  @Autowired
  private ValidationUtil validateUtil;

  @Override
  public void saveForum(ForumRequestDTO data) throws Exception {
    validateUtil.validate(data);

    User user = new User();
    user.setId(data.getUserId());

    Module module = new Module();
    module.setId(data.getModuleId());

    Forum forum = new Forum();
    forum.setUser(user);
    forum.setModule(module);

    forum.setContent(data.getContent());
    forum.setCreatedAt(LocalDateTime.now());
    forum.setCreatedBy(data.getUserId());
    forum.setTrxDate(LocalDate.now());
    forum.setTrxNumber(TransactionNumberUtils.generateForumTrxNumber());

    forumDao.saveForum(forum, null);
  }

  @Override
  public List<ForumModuleResponseDTO> getByModuleId(String moduleId) throws Exception {
    validateUtil.validateUUID(moduleId);
    List<ForumModuleResponseDTO> forumResponses =
        Optional.ofNullable(forumDao.getByModuleId(moduleId))
            .orElseThrow(() -> new DataIsNotExistsException("Module Id", moduleId));
    return forumResponses;
  }

  @Override
  public void deleteForum(String id, String userId) throws Exception {
    validateUtil.validateUUID(id, userId);
    if (forumDao.checkByForumIdAndUserId(id, userId) > 0) {
      try {
        begin();
        forumDao.deleteForum(id);
        commit();
      } catch (Exception e) {
        e.printStackTrace();
        rollback();
        throw e;
      }
    } else {
      throw new IllegalRequestException("User Id", userId);
    }

  }

}
