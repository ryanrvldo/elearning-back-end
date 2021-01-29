package com.lawencon.elearning.dto.experience;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceResponseDto {

  private String id;
  private String title;
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;

}
