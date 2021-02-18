package com.lawencon.elearning.dto.course.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseTypeResponseDTO {

  private String id;
  private String code;
  private String name;
  private Long version;
  private Boolean active;

}
