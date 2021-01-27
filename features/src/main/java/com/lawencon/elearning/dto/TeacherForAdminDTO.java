package com.lawencon.elearning.dto;

import com.lawencon.elearning.model.Gender;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */
@Data
public class TeacherForAdminDTO {

  private String id;
  private boolean isActive;
  private String code;
  private String phone;
  private Gender gender;
  private String username;
  private Long version;

}
