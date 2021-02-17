package com.lawencon.elearning.dto.file;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author Rian Rivaldo
 */
@Data
public class FileRequestDto {

  String id;

  @NotBlank
  String type;

  @NotBlank
  @Size(min = 32, max = 36)
  String userId;

}
