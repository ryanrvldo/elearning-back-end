package com.lawencon.elearning.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author Rian Rivaldo
 */
@Data
public class ExperienceRequestDTO {

  private String id;
  private String title;
  private String description;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String teacherId;
  private String userId;

}
