package com.lawencon.elearning.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Rian Rivaldo
 */
@Data
public class FileRequestDto {

  @NotBlank
  String type;

  @NotBlank
  String userId;

}
