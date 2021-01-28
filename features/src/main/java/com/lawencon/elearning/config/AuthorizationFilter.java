package com.lawencon.elearning.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

/**
 * @author Rian Rivaldo
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

  public AuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain)
      throws IOException, ServletException {
    String header = request.getHeader("Authorization");
    if (header == null || header.isEmpty() || !header.startsWith("Bearer")) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }

    String secretKey = "JDJhJDEwJFNQSXhydGhIeS56RmdiaWlJRmVlWnVvSHVqai50WVJqV1RHWGhnUzI3eS5xSjdLa1p6enNp";
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

    Claims claims;
    try {
      String bodyToken = header.replaceFirst("Bearer ", "");
      claims = Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(bodyToken)
          .getBody();
    } catch (Exception e) {
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      if (e instanceof ExpiredJwtException) {
        response.getWriter()
            .append(String.format("{\"code\":%d,", HttpStatus.UNAUTHORIZED.value()))
            .append("\"result\":\"Unauthorized request, your token is expired. Please get new token.\"}");
        return;
      }
      response.getWriter()
          .append(String.format("{\"code\":%d,", HttpStatus.UNAUTHORIZED.value()))
          .append("\"result\":\"Unauthorized request.\"}");
      return;
    }

    Authentication auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, null);
    SecurityContextHolder.getContext().setAuthentication(auth);
    chain.doFilter(request, response);
  }

}
