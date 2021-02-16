package com.lawencon.elearning.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class UpdatePasswordRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String id;

  @NotBlank
  private String oldPassword;

  @NotBlank
  private String newPassword;


}
