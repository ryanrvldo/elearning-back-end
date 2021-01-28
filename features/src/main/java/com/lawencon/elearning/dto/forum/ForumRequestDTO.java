package com.lawencon.elearning.dto.forum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class ForumRequestDTO {

  @NotBlank
  private String userId;

  @NotNull
  @Min(0)
  private Long versionUser;

  @NotBlank
  private String moduleId;

  @NotNull
  @Min(0)
  private Long versionModule;

  @NotNull
  private String content;


}
