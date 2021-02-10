package com.lawencon.elearning.dto.teacher;

import com.lawencon.elearning.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dzaky Fadhilla Guci
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherForAdminDTO {

  private String id;

  private String code;

  private String username;

  private String firstName;

  private String lastName;

  private String titleDegree;

  private String email;

  private String phone;

  private Gender gender;

  private String photoId;

  private boolean isActive;

}
