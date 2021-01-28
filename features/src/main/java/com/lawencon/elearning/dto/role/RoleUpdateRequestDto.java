package com.lawencon.elearning.dto.role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Rian Rivaldo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleUpdateRequestDto extends RoleCreateRequestDto {

  @NotBlank
  @Size(min = 36, message = "invalid format")
  private String id;

}
