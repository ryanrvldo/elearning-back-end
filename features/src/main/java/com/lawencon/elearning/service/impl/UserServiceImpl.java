package com.lawencon.elearning.service.impl;

import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Rian Rivaldo
 */
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Override
  public void addUser(User user) throws Exception {
    userDao.createUser(user);
  }

  @Override
  public User getById(String id) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException("id", id);
    }
    return userDao.findById(id);
  }

  @Override
  public User getByUsername(String username) throws Exception {
    if (username == null || username.trim().isEmpty()) {
      throw new IllegalRequestException("username", username);
    }
    return userDao.findByUsername(username);
  }

}
