package com.lawencon.elearning.dto.teacher;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.lawencon.elearning.model.Gender;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */
@Data
public class TeacherForAdminDTO {

  @NotBlank
  private String id;
  @NotBlank
  private String code;
  @NotBlank
  private String phone;
  @NotBlank
  private String username;
  @NotNull
  private Gender gender;
  @NotBlank
  private boolean isActive;
  @NotBlank
  private Long version;

}
