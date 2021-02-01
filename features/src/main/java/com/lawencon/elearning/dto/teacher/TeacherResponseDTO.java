package com.lawencon.elearning.dto.teacher;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.elearning.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Dzaky Fadhilla Guci
 */

@Data
@AllArgsConstructor
public class TeacherResponseDTO {

  private String firstName;

  private String lastName;

  private String email;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  private Gender gender;

  private String photoId;

}
