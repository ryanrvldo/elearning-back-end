package com.lawencon.elearning.dao.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.ForumDao;
import com.lawencon.elearning.model.Forum;
import com.lawencon.util.Callback;

/**
 * @author Dzaky Fadhilla Guci
 */

@Repository
public class ForumDaoImpl extends BaseDaoImpl<Forum> implements ForumDao, BaseCustomDao {

  @Override
  public List<Forum> getAllForums() throws Exception {
    return getAll();
  }

  @Override
  public void saveForum(Forum data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public Forum findForumById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public List<Forum> getByModuleId(String id) throws Exception {
    String sql = bBuilder(
        "SELECT trx_number , \"content\" , created_at , FROM tb_r_forums WHERE id_module =?1")
            .toString();

    List<Forum> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Forum forum = new Forum();
      forum.setTrxNumber((String) objArr[0]);
      forum.setContent((String) objArr[1]);
      Timestamp inDate = (Timestamp) objArr[2];
      forum.setCreatedAt((LocalDateTime) inDate.toLocalDateTime());

      listResult.add(forum);
    });
    return listResult.size() > 0 ? listResult : null;
  }


}
