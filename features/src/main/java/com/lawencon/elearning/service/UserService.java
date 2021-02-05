package com.lawencon.elearning.service;

import com.lawencon.elearning.model.User;

/**
 * @author Rian Rivaldo
 */
public interface UserService {

  void addUser(User user) throws Exception;

  User getById(String id) throws Exception;

  User getByUsername(String username) throws Exception;

  void updateUser(User user) throws Exception;

  void updateActivateStatus(String id, boolean status) throws Exception;

  String getUserRoleId(String userId) throws Exception;

  void deleteById(String id) throws Exception;

}
