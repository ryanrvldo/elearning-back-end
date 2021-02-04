package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.dto.forum.ForumModuleResponseDTO;
import com.lawencon.elearning.model.Forum;
import com.lawencon.util.Callback;

/**
 * @author Dzaky Fadhilla Guci
 */

public interface ForumDao {

  List<Forum> getAllForums() throws Exception;

  void saveForum(Forum data, Callback before) throws Exception;
  
  void updateForum(Forum data, Callback before) throws Exception;

  void deleteForum(String id) throws Exception;

  Forum findForumById(String id) throws Exception;

  List<ForumModuleResponseDTO> getByModuleId(String moduleId) throws Exception;

  Long checkByForumIdAndUserId(String id, String userId) throws Exception;
}
