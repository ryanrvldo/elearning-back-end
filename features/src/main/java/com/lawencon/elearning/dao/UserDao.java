package com.lawencon.elearning.dao;

import java.util.List;
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

  void updateUserPhoto(User user) throws Exception;

  String getUserRoleId(String userId) throws Exception;

  void deleteById(String id) throws Exception;

  void updatePasswordUser(String userId, String newPassword, String updatedBy) throws Exception;

  String getIdByEmail(String email) throws Exception;

  List<String> getEmailUsersPerModule(String idModule) throws Exception;

}
