package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.Role;
import java.util.List;

/**
 * @author Rian Rivaldo
 */
public interface RoleDao {

  void create(Role role) throws Exception;

  Role findById(String id) throws Exception;

  Role findByCode(String code) throws Exception;

  List<Role> findAll() throws Exception;

  void updateRole(Role role) throws Exception;

}
