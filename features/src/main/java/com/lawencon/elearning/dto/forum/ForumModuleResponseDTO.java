package com.lawencon.elearning.dto.forum;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.lawencon.elearning.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
@AllArgsConstructor
public class ForumModuleResponseDTO {

  @NotBlank
  private String id;

  @NotBlank
  private String code;

  @NotBlank
  private String content;

  @NotBlank
  private LocalDateTime createdAt;

  @NotNull
  private User user;

}
