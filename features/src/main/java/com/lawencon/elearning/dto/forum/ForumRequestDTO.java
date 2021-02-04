package com.lawencon.elearning.dto.forum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class ForumRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String userId;

  @NotBlank
  @Size(min = 32, max = 36)
  private String moduleId;

  @NotNull
  private String content;


}
