package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.WebResponseDTO;
import com.lawencon.elearning.dto.admin.DashboardResponseDto;
import com.lawencon.elearning.service.AdminService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * @author Rian Rivaldo
 */
@RestController
@RequestMapping(value = "/admin", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AdminController {

  @Autowired
  private AdminService adminService;

  @GetMapping(value = "/dashboard")
  public ResponseEntity<WebResponseDTO<DashboardResponseDto>> getDashboard() throws Exception {
    return WebResponseUtils.createWebResponse(adminService.getDashboard(), HttpStatus.OK);
  }

}
