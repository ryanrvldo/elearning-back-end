package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.ForumDao;
import com.lawencon.elearning.dto.forum.ForumModuleResponseDTO;
import com.lawencon.elearning.model.Forum;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

/**
 * @author Dzaky Fadhilla Guci
 */

@Repository
public class ForumDaoImpl extends CustomBaseDao<Forum> implements ForumDao {

  @Override
  public List<Forum> getAllForums() throws Exception {
    return getAll();
  }

  @Override
  public void saveForum(Forum data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public List<ForumModuleResponseDTO> getByModuleId(String moduleId) throws Exception {
    String sql = buildQueryOf(
        "SELECT trf.trx_number ,  trf.\"content\" , trf.created_at,  ",
        "tmu.id AS id_user, tmr.id AS id_role , tmr.code AS role_code, tmu.first_name , ",
        "tmu.last_name , tmu.id_photo, trf.id as id_forum ",
        "FROM tb_r_forums trf ",
        "INNER JOIN tb_m_users tmu ON trf.id_user = tmu.id  ",
        "INNER JOIN tb_m_roles tmr ON tmr.id = tmu.id_role  ",
        "WHERE id_module =?1 ORDER BY created_at")
        .toString();

    List<?> listObj = createNativeQuery(sql).setParameter(1, moduleId).getResultList();

    List<ForumModuleResponseDTO> listResult =
        HibernateUtils.bMapperList(listObj, ForumModuleResponseDTO.class, "code", "content",
            "createdAt", "userId", "roleId", "roleCode", "firstName", "lastName", "photoId", "id");

    return listResult.size() > 0 ? listResult : null;
  }

  @Override
  public void deleteForum(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public Long checkByForumIdAndUserId(String id, String userId) throws Exception {
    String sql =
        buildQueryOf("SELECT COUNT(*) FROM tb_r_forums WHERE id=?1 AND created_by=?2").toString();
    return Long.valueOf(createNativeQuery(sql).setParameter(1, id).setParameter(2, userId)
        .getSingleResult().toString());
  }



}
