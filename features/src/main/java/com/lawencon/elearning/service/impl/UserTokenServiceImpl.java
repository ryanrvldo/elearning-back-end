package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.UserTokenDao;
import com.lawencon.elearning.dto.EmailSetupDTO;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.GeneralCode;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.model.UserToken;
import com.lawencon.elearning.service.EmailService;
import com.lawencon.elearning.service.GeneralService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.service.UserTokenService;

/**
 * @author Rian Rivaldo
 */
@Service
public class UserTokenServiceImpl extends BaseServiceImpl implements UserTokenService {

  @Autowired
  private UserTokenDao userTokenDao;

  @Autowired
  private UserService userService;

  @Autowired
  private GeneralService generalService;

  @Autowired
  private EmailService emailService;

  @Override
  public UserToken findByToken(String token) throws Exception {
    if (token == null || token.trim().isEmpty()) {
      throw new IllegalRequestException("Can't process with empty token.");
    }
    try {
      return userTokenDao.findByToken(token);
    } catch (NoResultException e) {
      return null;
    }
  }

  @Override
  public void updateConfirmedAt(UserToken userToken) throws Exception {
    begin();
    userToken.setConfirmedAt(LocalDateTime.now());
    setupUpdatedValue(userToken, () -> userToken);
    userTokenDao.save(userToken);
    commit();
  }

  @Override
  public void sendUserTokenToEmail(String email) throws Exception {
    User user = userService.getByEmail(email);
    if (user.getIsActive()) {
      throw new IllegalRequestException("email", email);
    }

    String token = UUID.randomUUID().toString();
    LocalDateTime expiredDateTime = LocalDateTime.now().plusMinutes(30);
    String superAdminId = userService.getSuperAdminId();
    UserToken userToken = new UserToken(token, expiredDateTime, user);
    userToken.setCreatedBy(superAdminId);
    userTokenDao.save(userToken);

    String link = String
        .format("http://localhost:8080/lawerning/user/registration/confirm?token=%s", userToken.getToken());
    String template = generalService.getTemplateHTML(GeneralCode.USER_REGISTRATION.getCode())
        .replace("?1", link)
        .replace("?2",
            userToken.getExpiredAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    emailService.send(new EmailSetupDTO(
        email,
        "Lawerning Registration",
        "Confirm Registration",
        template));
  }
}
