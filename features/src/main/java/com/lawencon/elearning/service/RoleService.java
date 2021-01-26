package com.lawencon.elearning.service;

import com.lawencon.elearning.model.Role;
import java.util.List;

/**
 * @author Rian Rivaldo
 */
public interface RoleService {

  void create(Role role) throws Exception;

  Role findById(String id) throws Exception;

  Role findByCode(String code) throws Exception;

  List<Role> findAll() throws Exception;

}
