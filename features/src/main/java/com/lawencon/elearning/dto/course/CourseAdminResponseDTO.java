package com.lawencon.elearning.dto.course;

import java.time.LocalDate;
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
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate periodStart;
  private String status;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate periodEnd;
  private String description;
  private String typeId;
  private String categoryId;
  private String teacherId;
  private Boolean active;
  private String fullName;

}
