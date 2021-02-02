package com.lawencon.elearning.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * @author Rian Rivaldo
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;

  public AuthenticationFilter(AuthenticationManager authenticationManager,
      UserService userService) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    super.setFilterProcessesUrl("/authentication");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    User user = new User();
    try {
      user = new ObjectMapper().readValue(request.getInputStream(), User.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    String secretKey =
        "JDJhJDEwJFNQSXhydGhIeS56RmdiaWlJRmVlWnVvSHVqai50WVJqV1RHWGhnUzI3eS5xSjdLa1p6enNp";
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
    String token = Jwts.builder()
        .signWith(key)
        .setSubject(authResult.getName())
        .setExpiration(
            Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()))
        .compact();

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    try {
      User user = userService.getByUsername(authResult.getName());
      String userRoleId = userService.getUserRoleId(user.getId());
      response.getWriter()
          .append(String.format("{\"code\":%d,", HttpStatus.OK.value()))
          .append("\"result\":")
          .append(String.format("{\"token\":\"%s\",", token))
          .append(String.format("\"userId\":\"%s\",", user.getId()))
          .append(String.format("\"userRoleId\":\"%s\",", userRoleId))
          .append(String.format("\"username\":\"%s\",", user.getUsername()))
          .append(String.format("\"photoId\":\"%s\",", user.getUserPhoto().getId()))
          .append("\"role\":")
          .append(String.format("{\"id\":\"%s\",", user.getRole().getId()))
          .append(String.format("\"code\":\"%s\",", user.getRole().getCode()))
          .append(String.format("\"name\":\"%s\",", user.getRole().getName()))
          .append(String.format("\"version\":\"%s\"", user.getRole().getVersion()))
          .append("}}}");
    } catch (Exception e) {
      e.printStackTrace();
      response.getWriter()
          .append(String.format("{\"code\":%d,", HttpStatus.OK.value()))
          .append(String.format("\"result\":\"%s\"}", e.getMessage()));
    }
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.getWriter()
        .append(String.format("{\"code\":%d,", HttpStatus.UNAUTHORIZED.value()))
        .append("\"result\":\"Invalid user. Please try again.\"}");
  }
}
