package com.lawencon.elearning.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Rian Rivaldo
 */
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private ApiSecurityServiceImpl apiSecurityService;

  @Autowired
  private UserService userService;

  @Autowired
  private SecurityUtils encoderUtils;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .anyRequest()
        .authenticated();

    http.addFilter(
        new AuthenticationFilter(super.authenticationManager(), userService, objectMapper));
    http.addFilter(new AuthorizationFilter(super.authenticationManager(), objectMapper));
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(apiSecurityService)
        .passwordEncoder(encoderUtils.getEncoder());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers(HttpMethod.GET, "/file/**")
        .antMatchers(HttpMethod.GET, "/guest/**")
        .antMatchers(HttpMethod.POST, "/guest/student/register")
        .antMatchers(HttpMethod.GET, "/report/**")
        .antMatchers(HttpMethod.PATCH, "/user/email/**");
  }

  @Bean
  public WebMvcConfigurer coresConfigure() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:4200")
            .allowedMethods(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name());
      }
    };
  }
}
