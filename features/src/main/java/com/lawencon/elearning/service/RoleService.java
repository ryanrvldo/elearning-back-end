package com.lawencon.elearning.service;

import com.lawencon.elearning.dto.role.RoleCreateRequestDto;
import com.lawencon.elearning.dto.role.RoleResponseDto;
import com.lawencon.elearning.dto.role.RoleUpdateRequestDto;
import java.util.List;

/**
 * @author Rian Rivaldo
 */
public interface RoleService {

  void create(RoleCreateRequestDto request) throws Exception;

  RoleResponseDto findById(String id) throws Exception;

  RoleResponseDto findByCode(String code) throws Exception;

  List<RoleResponseDto> findAll() throws Exception;

  RoleResponseDto updateRole(RoleUpdateRequestDto roleRequestDto) throws Exception;

}
