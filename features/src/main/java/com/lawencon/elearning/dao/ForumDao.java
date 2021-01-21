package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.Forum;
import com.lawencon.util.Callback;

/**
 * @author Dzaky Fadhilla Guci
 */

public interface ForumDao {

  List<Forum> getAllForums() throws Exception;

  void saveForum(Forum data, Callback before) throws Exception;

  Forum findForumById(String id) throws Exception;

  List<Forum> getByModuleId(String id) throws Exception;
}
