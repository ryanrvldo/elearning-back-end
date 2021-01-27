package com.lawencon.elearning.dto;

import java.time.LocalDateTime;
import com.lawencon.elearning.model.CourseStatus;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseResponseDTO {

  private String courseId;
  private String courseCode;
  private String typeName;
  private Integer courseCapacity;
  private CourseStatus courseStatus;
  private String courseDescription;
  private LocalDateTime coursePeriodStart;
  private LocalDateTime coursePeriodEnd;
  private String teacherId;
  private String teacherCode;
  private String userFirstName;
  private String userLastName;
  private String teacherTittle;
  private String categoryCode;
  private String categoryName;

}
