package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import javax.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.dto.EmailSetupDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.GeneralCode;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.GeneralService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.MailUtils;
import com.lawencon.elearning.util.SecurityUtils;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author Rian Rivaldo
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private SecurityUtils encoderUtils;

  @Autowired
  private ValidationUtil validationUtil;

  @Autowired
  private GeneralService generalService;

  @Autowired
  private MailUtils mailUtils;

  @Override
  public void addUser(User user) throws Exception {
    user.setCreatedAt(LocalDateTime.now());
    String hash = encoderUtils.getHashPassword(user.getPassword());
    user.setPassword(hash);
    userDao.createUser(user);
  }

  @Override
  public User getById(String id) throws Exception {
    validationUtil.validateUUID(id);
    try {
      return userDao.findById(id);
    } catch (NoResultException e) {
      throw new DataIsNotExistsException("id", id);
    }
  }

  @Override
  public User getByUsername(String username) throws Exception {
    if (username == null || username.trim().isEmpty()) {
      throw new IllegalRequestException("username", username);
    }
    try {
      return userDao.findByUsername(username);
    } catch (NoResultException e) {
      throw new DataIsNotExistsException("username", username);
    }
  }

  @Override
  public void updateUser(User user) throws Exception {
    Optional.ofNullable(getById(user.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("user id", user.getId()));
    userDao.updateUser(user);
  }

  @Override
  public void updateActivateStatus(String id, boolean status) throws Exception {
    begin();
    userDao.updateActivateStatus(id, status);
    commit();
  }

  @Override
  public String getUserRoleId(String userId) throws Exception {
    User user = getById(userId);
    String id = userDao.getUserRoleId(user.getId());
    if (id == null) {
      return null;
    } else if (id != null && id.trim().isEmpty()) {
      throw new DataIsNotExistsException("user id", userId);
    }
    return id;
  }

  @Override
  public void deleteById(String id) throws Exception {
    validationUtil.validateUUID(id);
    userDao.deleteById(id);
  }

  @Override
  public void updatePasswordUser(String userId, String newPassword) throws Exception {
    validationUtil.validateUUID(userId);
    User checkUser = Optional.ofNullable(getById(userId))
        .orElseThrow(() -> new DataIsNotExistsException("Id", userId));

    newPassword = encoderUtils.getHashPassword(newPassword);
    try {
      begin();
      userDao.updatePasswordUser(checkUser.getId(), newPassword, checkUser.getId());
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public void resetPassword(String email) throws Exception {
    String userId = Optional.ofNullable(userDao.getIdByEmail(email))
        .orElseThrow(() -> new DataIsNotExistsException("email", email));

    String superAdminId = "62615b14-a30a-4ec7-9ded-0b2d09012a5d";
    String generatePass = generatePassString();

    String template = generalService.getTemplateHTML(GeneralCode.RESET_PASSWORD.getCode());
    template = template.replace("?1", generatePass);

    String[] emailTo = {"lawerning.acc@gmail.com"};
    EmailSetupDTO emailSent = new EmailSetupDTO();
    emailSent.setTo(emailTo);
    emailSent.setSubject("Reset Password");
    emailSent.setBody(template);
    String savePassword = encoderUtils.getHashPassword(generatePass);

    try {
      begin();
      userDao.updatePasswordUser(userId, savePassword, superAdminId);
      new EmailServiceImpl(mailUtils, emailSent).start();
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }


  private String generatePassString() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 7) {
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr;

  }

}
