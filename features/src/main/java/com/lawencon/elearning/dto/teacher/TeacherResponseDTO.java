package com.lawencon.elearning.dto.teacher;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.elearning.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
@AllArgsConstructor
public class TeacherResponseDTO {

  @NotBlank
  private String firstName;

  private String lastName;

  @NotBlank
  private String email;

  @NotBlank
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @NotNull
  private Gender gender;

  @NotNull
  private String photoId;


}
