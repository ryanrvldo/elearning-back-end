package com.lawencon.elearning.dao;

import com.lawencon.elearning.model.UserToken;

/**
 * @author Rian Rivaldo
 */
public interface UserTokenDao {

  void save(UserToken userToken) throws Exception;

  UserToken findByToken(String token) throws Exception;

}
