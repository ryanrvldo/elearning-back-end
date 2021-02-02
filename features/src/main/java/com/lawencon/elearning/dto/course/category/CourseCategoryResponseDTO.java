package com.lawencon.elearning.dto.course.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCategoryResponseDTO {

  private String id;
  private String code;
  private String name;
  private Long version;

}
