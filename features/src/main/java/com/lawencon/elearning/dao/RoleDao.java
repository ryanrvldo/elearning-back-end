package com.lawencon.elearning.dao;

import java.util.List;
import com.lawencon.elearning.model.Role;

/**
 * @author Rian Rivaldo
 */
public interface RoleDao {

  void create(Role role) throws Exception;

  Role findById(String id) throws Exception;

  Role findByCode(String code) throws Exception;

  List<Role> findAll() throws Exception;

}
