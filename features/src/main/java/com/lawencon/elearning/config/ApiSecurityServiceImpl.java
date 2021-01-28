package com.lawencon.elearning.config;

import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.UserService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Rian Rivaldo
 */
@Service
public class ApiSecurityServiceImpl implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      User validatedUser = userService.getByUsername(username);
      if (validatedUser != null) {
        return new org.springframework.security.core.userdetails.User(validatedUser.getUsername(),
            validatedUser.getPassword(), Collections.emptyList());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
