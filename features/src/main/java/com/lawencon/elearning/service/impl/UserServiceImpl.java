package com.lawencon.elearning.service.impl;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.EncoderUtils;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rian Rivaldo
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private EncoderUtils encoderUtils;

  @Override
  public void addUser(User user) throws Exception {
    user.setCreatedAt(LocalDateTime.now());
    String hash = encoderUtils.getHashPassword(user.getPassword());
    user.setPassword(hash);
    userDao.createUser(user);
  }

  @Override
  public User getById(String id) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException("id", id);
    }
    return Optional.ofNullable(userDao.findById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public User getByUsername(String username) throws Exception {
    if (username == null || username.trim().isEmpty()) {
      throw new IllegalRequestException("username", username);
    }
    return Optional.ofNullable(userDao.findByUsername(username))
        .orElseThrow(() -> new DataIsNotExistsException("username", username));
  }

  @Override
  public void updateUser(User user) throws Exception {
    setupUpdatedValue(user, () -> getById(user.getId()));
    userDao.updateUser(user);
  }

  @Override
  public void updateActivateStatus(String id, boolean status) throws Exception {
    begin();
    userDao.updateActivateStatus(id, status);
    commit();
  }

}
