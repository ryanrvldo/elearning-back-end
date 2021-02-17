package com.lawencon.elearning.config;

import static com.lawencon.elearning.util.WebResponseUtils.createFailedAuthResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.dto.TokenResponseDto;
import com.lawencon.elearning.dto.role.RoleResponseDto;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.WebResponseUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

/**
 * @author Rian Rivaldo
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final ObjectMapper objectMapper;

  public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService,
      ObjectMapper objectMapper) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.objectMapper = objectMapper;
    super.setFilterProcessesUrl("/authentication");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    User user = new User();
    try {
      user = objectMapper.readValue(request.getInputStream(), User.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    String KEY = "VPnEQ4KjAfPk8LHxxvyoQF52RgWagpgLPTUaZXa26caoFGS9ddnpwdgVwWKXiyI1vM9KRzNai-2L7GLym_SMoUFI65kPeiHHSfwF-y28vNUBlXia-300JoWaqdm644XwsWui05leT6bRFjXyqWKxLzKsy36Zm7NPyS2l1pRqfBEEOZgeuI1LO2uim9RYuYxTnweAQndFx0WEX-Pe3pHlxUNxnn0lpOi_fvF7KCVto43cAV0-WCPBe-eNi7SEPs8ZNkgu0DKFXcCeeAqVnNTNIOyYKNNmCnr7qzuvaBhBkeqHVevZU7HJma347fFvdM0SVeAEX8HxgTsBPtpEUjqB";
    SecretKey key = Keys.hmacShaKeyFor(KEY.getBytes());
    String token = Jwts.builder()
        .signWith(key)
        .setSubject(authResult.getName())
        .setExpiration(
            Date.from(LocalDateTime.now()
                .plusDays(1)
                .atZone(ZoneId.systemDefault())
                .toInstant()))
        .compact();

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    try {
      User user = userService.getByUsername(authResult.getName());
      String userRoleId = userService.getUserRoleId(user.getId());

      RoleResponseDto roleResponse = new RoleResponseDto();
      roleResponse.setId(user.getRole().getId());
      roleResponse.setCode(user.getRole().getCode());
      roleResponse.setName(user.getRole().getName());

      TokenResponseDto tokenResponse = new TokenResponseDto(token, user.getId(), userRoleId,
          user.getFirstName(), user.getLastName(), user.getUsername(), user.getUserPhoto().getId(),
          roleResponse);

      response.getWriter().write(objectMapper
          .writeValueAsString(WebResponseUtils.createSuccessAuthResponse(tokenResponse)));
    } catch (Exception e) {
      e.printStackTrace();
      response.getWriter()
          .write(objectMapper.writeValueAsString(
              createFailedAuthResponse(e.getMessage())));
    }
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.getWriter().write(objectMapper.writeValueAsString(
        createFailedAuthResponse("Invalid user. Please try again.")));
  }
}
