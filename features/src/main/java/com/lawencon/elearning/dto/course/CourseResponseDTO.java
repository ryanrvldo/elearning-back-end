package com.lawencon.elearning.dto.course;

import java.time.LocalDateTime;
import com.lawencon.elearning.dto.teacher.TeacherForAvailableCourseDTO;
import com.lawencon.elearning.model.CourseStatus;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseResponseDTO {


  private String id;

  private String code;

  private String typeName;

  private Integer capacity;

  private CourseStatus courseStatus;

  private String courseDescription;

  private LocalDateTime periodStart;

  private LocalDateTime periodEnd;

  private String categoryName;

  private TeacherForAvailableCourseDTO teacher;


}
