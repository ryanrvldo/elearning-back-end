package com.lawencon.elearning.service;

import com.lawencon.elearning.model.User;

/**
 * @author Rian Rivaldo
 */
public interface UserService {

  void addUser(User user) throws Exception;

  User getById(String id) throws Exception;

  User getByUsername(String username) throws Exception;

}
