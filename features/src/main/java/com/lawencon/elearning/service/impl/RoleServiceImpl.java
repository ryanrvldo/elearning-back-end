package com.lawencon.elearning.service.impl;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.RoleDao;
import com.lawencon.elearning.dto.role.RoleCreateRequestDto;
import com.lawencon.elearning.dto.role.RoleResponseDto;
import com.lawencon.elearning.dto.role.RoleUpdateRequestDto;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.service.RoleService;
import com.lawencon.elearning.util.ValidationUtil;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rian Rivaldo
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

  @Autowired
  private RoleDao roleDao;

  @Autowired
  private ValidationUtil validationUtil;

  @Override
  public void create(RoleCreateRequestDto request) throws Exception {
    validationUtil.validate(request);

    Role role = new Role();
    role.setCreatedAt(LocalDateTime.now());
    role.setName(request.getName());
    role.setCode(request.getCode());
    role.setCreatedBy(request.getUserId());
    roleDao.create(role);
  }

  @Override
  public RoleResponseDto findById(String id) throws Exception {
    if (id.trim().isEmpty()) {
      throw new IllegalRequestException("id", id);
    }
    Role role = Optional.ofNullable(roleDao.findById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
    return mapEntityToResponse(role);
  }

  @Override
  public Role findByCode(String code) throws Exception {
    if (code.trim().isEmpty()) {
      throw new IllegalRequestException("code", code);
    }
    return Optional.ofNullable(roleDao.findByCode(code))
        .orElseThrow(() -> new DataIsNotExistsException("code", code));
  }

  @Override
  public List<RoleResponseDto> findAll() throws Exception {
    List<Role> roles = roleDao.findAll();
    if (roles.isEmpty()) {
      return Collections.emptyList();
    }
    return roles.stream()
        .map(this::mapEntityToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public RoleResponseDto updateRole(RoleUpdateRequestDto roleRequestDto) throws Exception {
    validationUtil.validate(roleRequestDto);

    Role role = new Role();
    role.setCode(roleRequestDto.getCode());
    role.setName(roleRequestDto.getName());
    role.setUpdatedBy(roleRequestDto.getUserId());
    setupUpdatedValue(role, () -> Optional.ofNullable(roleDao.findById(roleRequestDto.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("id", roleRequestDto.getId())));
    roleDao.updateRole(role);

    return new RoleResponseDto(
        role.getId(),
        role.getCode(),
        role.getName(),
        role.getVersion() + 1,
        role.getCreatedAt(),
        role.getUpdatedAt());
  }

  private RoleResponseDto mapEntityToResponse(Role role) {
    return new RoleResponseDto(
        role.getId(),
        role.getCode(),
        role.getName(),
        role.getVersion(),
        role.getCreatedAt(),
        role.getUpdatedAt());
  }

}
