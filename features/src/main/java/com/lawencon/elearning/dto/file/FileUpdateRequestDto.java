package com.lawencon.elearning.dto.file;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUpdateRequestDto {

  @NotBlank
  @Size(max = 36)
  private String id;

  @NotBlank
  @Size(max = 36)
  private String userId;

}
