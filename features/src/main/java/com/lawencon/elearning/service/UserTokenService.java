package com.lawencon.elearning.service;

import com.lawencon.elearning.model.UserToken;

/**
 * @author Rian Rivaldo
 */
public interface UserTokenService {

  UserToken findByToken(String token) throws Exception;

  void updateConfirmedAt(UserToken userToken) throws Exception;

  void sendUserTokenToEmail(String email) throws Exception;

}
