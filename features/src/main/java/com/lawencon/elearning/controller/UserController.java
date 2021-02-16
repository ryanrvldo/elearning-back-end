package com.lawencon.elearning.controller;

import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.WebResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @author Dzaky Fadhilla Guci
 */

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @PatchMapping("/user")
  public ResponseEntity<?> updatePassword(@RequestParam("userId") String userId,
      @RequestParam("newPassword") String newPassword) throws Exception {
    userService.updatePasswordUser(userId, newPassword);
    return WebResponseUtils.createWebResponse("Update Password Success", HttpStatus.OK);
  }

}
