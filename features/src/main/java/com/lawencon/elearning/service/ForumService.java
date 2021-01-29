package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.forum.ForumModuleResponseDTO;
import com.lawencon.elearning.dto.forum.ForumRequestDTO;
import com.lawencon.elearning.model.Forum;

/**
 *  @author Dzaky Fadhilla Guci
 */

public interface ForumService {

  List<Forum> getAllForums() throws Exception;

  void saveForum(ForumRequestDTO data) throws Exception;

  void updateForum(Forum data) throws Exception;

  void deleteForum(String id) throws Exception;

  Forum findForumById(String id) throws Exception;

  List<ForumModuleResponseDTO> getByModuleId(String moduleId) throws Exception;

}
