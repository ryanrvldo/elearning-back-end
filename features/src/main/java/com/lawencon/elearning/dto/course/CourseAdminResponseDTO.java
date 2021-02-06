package com.lawencon.elearning.dto.course;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseAdminResponseDTO {

  private String id;
  private String code;
  private String categoryName;
  private String typeName;
  private Integer capacity;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodStart;
  private String status;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime periodEnd;
  private String description;
}
