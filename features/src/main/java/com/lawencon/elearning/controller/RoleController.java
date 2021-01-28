package com.lawencon.elearning.controller;

import com.lawencon.elearning.dto.role.RoleCreateRequestDto;
import com.lawencon.elearning.dto.role.RoleUpdateRequestDto;
import com.lawencon.elearning.service.RoleService;
import com.lawencon.elearning.util.WebResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WILLIAM
 */

@RestController
public class RoleController {

  @Autowired
  private RoleService roleService;

  @GetMapping("/roles")
  public ResponseEntity<?> getAll() throws Exception {
    return WebResponseUtils.createWebResponse(roleService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/role/id/{id}")
  public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(roleService.findById(id), HttpStatus.OK);
  }

  @GetMapping("/role/code/{code}")
  public ResponseEntity<?> getByCode(@PathVariable("code") String code) throws Exception {
    return WebResponseUtils.createWebResponse(roleService.findByCode(code), HttpStatus.OK);
  }

  @PostMapping(value = {"/role"})
  public ResponseEntity<?> insertRole(@RequestBody RoleCreateRequestDto body) throws Exception {
    roleService.create(body);
    return WebResponseUtils.createWebResponse("Insert Success", HttpStatus.CREATED);
  }

  @PutMapping(value = {"/role"})
  public ResponseEntity<?> updateRole(@RequestBody RoleUpdateRequestDto body) throws Exception {
    return WebResponseUtils.createWebResponse(roleService.updateRole(body), HttpStatus.OK);
  }

}
