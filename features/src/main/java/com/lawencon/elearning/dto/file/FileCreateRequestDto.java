package com.lawencon.elearning.dto.file;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Rian Rivaldo
 */
@Data
public class FileCreateRequestDto {

  @NotBlank
  String type;

  @NotBlank
  String userId;

}
