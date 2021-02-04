package com.lawencon.elearning.service;

import java.util.List;
import com.lawencon.elearning.dto.role.RoleCreateRequestDto;
import com.lawencon.elearning.dto.role.RoleResponseDto;
import com.lawencon.elearning.dto.role.RoleUpdateRequestDto;
import com.lawencon.elearning.model.Role;

/**
 * @author Rian Rivaldo
 */
public interface RoleService {

  void create(RoleCreateRequestDto request) throws Exception;

  RoleResponseDto findById(String id) throws Exception;

  Role findByCode(String code) throws Exception;

  List<RoleResponseDto> findAll() throws Exception;

  RoleResponseDto updateRole(RoleUpdateRequestDto roleRequestDto) throws Exception;

}
