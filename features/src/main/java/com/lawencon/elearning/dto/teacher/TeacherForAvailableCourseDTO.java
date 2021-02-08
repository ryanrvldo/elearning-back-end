package com.lawencon.elearning.dto.teacher;

import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class TeacherForAvailableCourseDTO {

  private String id;
  private String code;
  private String firstName;
  private String lastName;
  private String title;
  private String experience;
  private String photoId;
}
