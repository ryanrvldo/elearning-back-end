package com.lawencon.elearning.dto.teacher;

import com.lawencon.elearning.model.Gender;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */
@Data
public class TeacherForAdminDTO {


  private String id;

  private String code;

  private String phone;

  private String username;

  private Gender gender;

  private boolean isActive;

  private Long version;

}
