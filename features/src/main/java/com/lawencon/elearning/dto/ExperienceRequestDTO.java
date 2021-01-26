package com.lawencon.elearning.dto;

import java.time.LocalDate;
import lombok.Data;

/**
 * @author Rian Rivaldo
 */
@Data
public class ExperienceRequestDTO {

  private String id;
  private String title;
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;
  private String teacherId;
  private String userId;

}
