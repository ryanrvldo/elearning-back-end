package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.RoleDao;
import com.lawencon.elearning.model.Role;

/**
 * @author Rian Rivaldo
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

  @Override
  public void create(Role role) throws Exception {
    save(role, null, null, true, true);
  }

  @Override
  public Role findById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public Role findByCode(String code) throws Exception {
    String query = new StringBuilder().append("FROM Role WHERE code = ?1 ").toString();
    return createQuery(query, Role.class).setParameter(1, code).getSingleResult();
  }

  @Override
  public List<Role> findAll() throws Exception {
    return getAll();
  }

}
