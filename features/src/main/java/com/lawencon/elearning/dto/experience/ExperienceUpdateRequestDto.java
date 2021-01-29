package com.lawencon.elearning.dto.experience;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Rian Rivaldo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExperienceUpdateRequestDto extends ExperienceCreateRequestDTO {

  @NotBlank
  @Size(min = 32)
  private String id;

}
