package com.lawencon.elearning.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * @author Rian Rivaldo
 */
@RequestMapping(value = "/admin", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AdminController {

  @GetMapping(value = "/dashboard")
  public ResponseEntity<?> getDashboard() throws Exception {
    return WebResponseUtils.createWebResponse("Under development.", HttpStatus.OK);
  }

}
