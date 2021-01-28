package com.lawencon.elearning.dto.role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author Rian Rivaldo
 */
@Data
public class RoleCreateRequestDto {

  @NotBlank
  @Size(max = 50)
  private String code;

  @NotBlank
  @Size(max = 50)
  private String name;

  @NotBlank
  @Size(min = 36, message = "invalid format")
  private String userId;

}
