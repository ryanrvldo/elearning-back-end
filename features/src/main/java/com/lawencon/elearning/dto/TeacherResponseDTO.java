package com.lawencon.elearning.dto;

import java.time.LocalDateTime;
import com.lawencon.elearning.model.Gender;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class TeacherResponseDTO {

  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private LocalDateTime createdAt;
  private Gender gender;

  public TeacherResponseDTO(String firstName, String lastName, String email,
      LocalDateTime createdAt, Gender gender) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.createdAt = createdAt;
    this.gender = gender;
  }



  public TeacherResponseDTO(String id, String firstName, String lastName, String email,
      LocalDateTime createdAt, Gender gender) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.createdAt = createdAt;
    this.gender = gender;
  }



}
