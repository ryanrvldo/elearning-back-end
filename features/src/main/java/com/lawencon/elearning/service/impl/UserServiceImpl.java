package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.dto.EmailSetupDTO;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.UpdatePasswordRequestDTO;
import com.lawencon.elearning.dto.file.FileRequestDto;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.error.DataAlreadyExistException;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.error.InternalServerErrorException;
import com.lawencon.elearning.model.GeneralCode;
import com.lawencon.elearning.model.Roles;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.model.UserToken;
import com.lawencon.elearning.service.EmailService;
import com.lawencon.elearning.service.FileService;
import com.lawencon.elearning.service.GeneralService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.service.TeacherService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.service.UserTokenService;
import com.lawencon.elearning.util.SecurityUtils;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author Rian Rivaldo
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserDao userDao;

  @Autowired
  private GeneralService generalService;

  @Autowired
  private FileService fileService;

  @Autowired
  private EmailService emailService;

  @Autowired
  private UserTokenService userTokenService;

  @Autowired
  private StudentService studentService;

  @Autowired
  private TeacherService teacherService;

  @Autowired
  private SecurityUtils encoderUtils;

  @Autowired
  private ValidationUtil validationUtil;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void addUser(User user) throws Exception {
    user.setCreatedAt(LocalDateTime.now());
    String hash = encoderUtils.getHashPassword(user.getPassword());
    user.setPassword(hash);
    try {
      String id = userDao.getIdByEmail(user.getEmail());
      if (id != null) {
        throw new DataAlreadyExistException("email", user.getEmail());
      }
    } catch (NoResultException e) {
      userDao.createUser(user);
    }
  }

  @Override
  public String confirmUserRegistration(String token) throws Exception {
    validationUtil.validateUUID(token);

    UserToken userToken = Optional.ofNullable(userTokenService.findByToken(token))
        .orElseThrow(() -> new DataIsNotExistsException("token", token));
    User user = userToken.getUser();

    if (user.getIsActive() || userToken.getConfirmedAt() != null) {
      String body = generalService.getTemplateHTML(GeneralCode.USER_REGISTER_CONFIRMATION_FAILED
          .getCode());
      return emailService.buildHtmlContent("Registration has been confirmed.", body);
    } else if (userToken.getExpiredAt().isBefore(LocalDateTime.now())) {
      String generateTokenLink = String
          .format("http://localhost:8080/lawerning/user/registration/resend?e=%s",
              userToken.getUser().getEmail());
      String body = generalService.getTemplateHTML(GeneralCode.TOKEN_EXPIRED.getCode())
          .replace("?1", generateTokenLink);
      return emailService.buildHtmlContent("Your token already expired.", body);
    }

    String superAdminUserId = userDao.findSuperAdminId();
    UpdateIsActiveRequestDTO updateRequest = new UpdateIsActiveRequestDTO();
    updateRequest.setUpdatedBy(superAdminUserId);
    updateRequest.setStatus(true);
    if (user.getRole().getCode().equals(Roles.STUDENT.getCode())) {
      Student student = studentService.getStudentByIdUser(user.getId());
      updateRequest.setId(student.getId());
      studentService.updateIsActive(updateRequest);
    } else if (user.getRole().getCode().equals(Roles.TEACHER.getCode())) {
      Teacher teacher = teacherService.getByUserId(user.getId());
      updateRequest.setId(teacher.getId());
      teacherService.updateIsActive(updateRequest);
    }
    userToken.setUpdatedBy(superAdminUserId);
    userTokenService.updateConfirmedAt(userToken);

    String body = generalService
        .getTemplateHTML(GeneralCode.USER_REGISTER_CONFIRMATION_SUCCESS.getCode());
    return emailService.buildHtmlContent("Register Confirmation", body);
  }

  @Override
  public String sendNewToken(String email) throws Exception {
    String body;
    try {
      begin();
      userTokenService.sendUserTokenToEmail(email);
      commit();
      body = generalService.getTemplateHTML(GeneralCode.TOKEN_GENERATED.getCode());
      return emailService.buildHtmlContent("Success.", body);
    } catch (IllegalRequestException e) {
      body = generalService
          .getTemplateHTML(GeneralCode.USER_REGISTER_CONFIRMATION_FAILED.getCode());
      return emailService.buildHtmlContent("You are already activated.", body);
    }
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
  public User getByEmail(String email) throws Exception {
    if (email == null || email.trim().isEmpty()) {
      throw new IllegalRequestException("email", email);
    }
    try {
      return userDao.findByEmail(email);
    } catch (NoResultException e) {
      throw new DataIsNotExistsException("email", email);
    }
  }

  @Override
  public String getSuperAdminId() throws Exception {
    return userDao.findSuperAdminId();
  }

  @Override
  public void updateUser(User user) throws Exception {
    Optional.ofNullable(getById(user.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("user id", user.getId()));
    userDao.updateUser(user);
  }

  @Override
  public void updateActivateStatus(String id, Boolean status, String updatedBy) throws Exception {
    userDao.updateActivateStatus(id, status, updatedBy);
  }

  @Override
  public String getUserRoleId(String userId) throws Exception {
    User user = getById(userId);
    String id = userDao.getUserRoleId(user.getId());
    if (id == null) {
      return null;
    } else if (id.trim().isEmpty()) {
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
  public void updatePasswordUser(UpdatePasswordRequestDTO request) throws Exception {
    validationUtil.validate(request);
    User checkUser = Optional.ofNullable(getById(request.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("Id", request.getId()));

    boolean bool =
        encoderUtils.getEncoder().matches(request.getOldPassword(), checkUser.getPassword());

    if (!bool) {
      throw new IllegalRequestException("password not match");
    }

    String newPassword = encoderUtils.getHashPassword(request.getNewPassword());
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

    String generatePass = generatePassString();

    String template = generalService.getTemplateHTML(GeneralCode.RESET_PASSWORD.getCode())
        .replace("?1", generatePass);

    EmailSetupDTO emailSent = new EmailSetupDTO(
        email,
        "Reset Password",
        "Reset Password",
        template);
    String savePassword = encoderUtils.getHashPassword(generatePass);

    try {
      begin();
      String superAdminUserId = userDao.findSuperAdminId();
      userDao.updatePasswordUser(userId, savePassword, superAdminUserId);
      emailService.send(emailSent);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

  @Override
  public void saveUserPhoto(MultipartFile file, String content) throws Exception {
    FileRequestDto fileRequest;
    try {
      fileRequest = objectMapper.readValue(content, FileRequestDto.class);
      validationUtil.validate(fileRequest);
    } catch (JsonProcessingException e) {
      throw new IllegalRequestException("Invalid file content.");
    }

    String contentType = Objects.requireNonNull(file.getContentType());
    if (!contentType.equalsIgnoreCase(MediaType.IMAGE_PNG_VALUE) && !contentType
        .equalsIgnoreCase(MediaType.IMAGE_JPEG_VALUE)) {
      throw new IllegalRequestException("content type", file.getContentType());
    }

    User user = getById(fileRequest.getUserId());
    LOGGER.info(user.toString());
    if (user.getUserPhoto().getId() != null) {
      validationUtil.validateUUID(fileRequest.getId(), fileRequest.getUserId());
      fileService.updateFile(file, fileRequest);
      user.getUserPhoto().setId(fileRequest.getId());
    } else {
      validationUtil.validateUUID(fileRequest.getUserId());
      begin();
      FileResponseDto fileResponse = Optional.ofNullable(fileService.createFile(file, fileRequest))
          .orElseThrow(() -> new InternalServerErrorException("Failed to save user photo."));
      commit();
      user.getUserPhoto().setId(fileResponse.getId());
    }
    user.setUpdatedBy(fileRequest.getId());
    begin();
    userDao.updateUserPhoto(user);
    commit();
  }

  private String generatePassString() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 7) {
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    return salt.toString();
  }

  @Override
  public List<String> getEmailUsersPerModule(String idModule) throws Exception {
    return userDao.getEmailUsersPerModule(idModule);
  }

}
