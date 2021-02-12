package com.lawencon.elearning.config;

import static com.lawencon.elearning.util.WebResponseUtils.createFailedAuthResponse;
import java.io.IOException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * @author Rian Rivaldo
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

  private final String KEY =
      "VPnEQ4KjAfPk8LHxxvyoQF52RgWagpgLPTUaZXa26caoFGS9ddnpwdgVwWKXiyI1vM9KRzNai-2L7GLym_SMoUFI65kPeiHHSfwF-y28vNUBlXia-300JoWaqdm644XwsWui05leT6bRFjXyqWKxLzKsy36Zm7NPyS2l1pRqfBEEOZgeuI1LO2uim9RYuYxTnweAQndFx0WEX-Pe3pHlxUNxnn0lpOi_fvF7KCVto43cAV0-WCPBe-eNi7SEPs8ZNkgu0DKFXcCeeAqVnNTNIOyYKNNmCnr7qzuvaBhBkeqHVevZU7HJma347fFvdM0SVeAEX8HxgTsBPtpEUjqB";

  private ObjectMapper objectMapper;

  public AuthorizationFilter(AuthenticationManager authManager, ObjectMapper objectMapper) {
    super(authManager);
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain)
      throws IOException, ServletException {
    String header = request.getHeader("Authorization");
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    if (header == null || header.isEmpty() || !header.startsWith("Bearer")) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write(objectMapper.writeValueAsString(
          createFailedAuthResponse("Please put your token key in your header request.")));
      return;
    }

    SecretKey key = Keys.hmacShaKeyFor(KEY.getBytes());

    Claims claims;
    try {
      String bodyToken = header.replaceFirst("Bearer ", "");
      claims = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(bodyToken)
          .getBody();
    } catch (Exception e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      if (e instanceof ExpiredJwtException) {
        response.getWriter().write(objectMapper.writeValueAsString(
            createFailedAuthResponse("Your token is expired. Please get new token.")));
        return;
      }
      response.getWriter().write(
          objectMapper.writeValueAsString(createFailedAuthResponse("Unauthorized request.")));
      return;
    }

    Authentication auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
    chain.doFilter(request, response);
  }

}
