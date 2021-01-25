package com.lawencon.elearning.dao.impl;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.RoleDao;
import com.lawencon.elearning.model.Role;
import java.util.List;
import org.springframework.stereotype.Repository;

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
    return createQuery("FROM Role WHERE code = ?1 ", Role.class)
        .setParameter(1, code)
        .getSingleResult();
  }

  @Override
  public List<Role> findAll() throws Exception {
    return getAll();
  }

}
