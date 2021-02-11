package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.forum.ForumModuleResponseDTO;
import com.lawencon.elearning.dto.forum.ForumRequestDTO;

/**
 *  @author Dzaky Fadhilla Guci
 */

public interface ForumService {

  void saveForum(ForumRequestDTO data) throws Exception;

  void deleteForum(String id, String userId) throws Exception;

  List<ForumModuleResponseDTO> getByModuleId(String moduleId) throws Exception;

}
