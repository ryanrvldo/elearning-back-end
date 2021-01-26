package com.lawencon.elearning.service.impl;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.RoleDao;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.service.RoleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rian Rivaldo
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

  @Autowired
  private RoleDao roleDao;

  @Override
  public void create(Role role) throws Exception {
    roleDao.create(role);
  }

  @Override
  public Role findById(String id) throws Exception {
    return Optional.ofNullable(roleDao.findById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public Role findByCode(String code) throws Exception {
    return Optional.ofNullable(roleDao.findByCode(code))
        .orElseThrow(() -> new DataIsNotExistsException("code", code));
  }

  @Override
  public List<Role> findAll() throws Exception {
    return roleDao.findAll();
  }

}
