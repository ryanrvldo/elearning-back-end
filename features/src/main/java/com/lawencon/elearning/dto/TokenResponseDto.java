package com.lawencon.elearning.dto;

import com.lawencon.elearning.dto.role.RoleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {

  private String token;

  private String userId;

  private String userRoleId;

  private String username;

  private String photoId;

  private RoleResponseDto role;

}
