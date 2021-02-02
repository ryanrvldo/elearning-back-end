package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.User;

/**
 * @author Rian Rivaldo
 */
public interface UserDao {

  void createUser(User user) throws Exception;

  User findById(String id) throws Exception;

  User findByUsername(String username) throws Exception;

  void updateUser(User user) throws Exception;

  void updateActivateStatus(String id, boolean status) throws Exception;

  String getUserRoleId(String userId) throws Exception;

}
