package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.UpdatePasswordRequestDTO;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 *  @author Dzaky Fadhilla Guci
 */

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @PatchMapping("/user/email/{email}")
  public ResponseEntity<?> resetPassword(@PathVariable("email") String email) throws Exception {
    userService.resetPassword(email);
    return WebResponseUtils.createWebResponse("Reset Password Success", HttpStatus.OK);
  }
  
  @PatchMapping("/user")
  public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequestDTO request)
      throws Exception {
    userService.updatePasswordUser(request);
    return WebResponseUtils.createWebResponse("Update Password Success", HttpStatus.OK);
  }

}
