package com.lawencon.elearning.dto;

import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.Schedule;
import com.lawencon.elearning.model.SubjectCategory;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ModuleResponseDTO {

  private String id;
  private String code;
  private String tittle;
  private String description;
  private Schedule schedule;
  private Course course;
  private SubjectCategory subject;
  
  public ModuleResponseDTO(String id, String code, String tittle, String description,
      Schedule schedule, Course course, SubjectCategory subject) {

    this.id = id;
    this.code = code;
    this.tittle = tittle;
    this.description = description;
    this.schedule = schedule;
    this.course = course;
    this.subject = subject;
  }

  public ModuleResponseDTO(String code, String tittle, String description, Schedule schedule,
      Course course, SubjectCategory subject) {
    this.code = code;
    this.tittle = tittle;
    this.description = description;
    this.schedule = schedule;
    this.course = course;
    this.subject = subject;
  }

}
