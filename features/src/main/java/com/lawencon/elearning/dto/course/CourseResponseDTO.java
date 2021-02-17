package com.lawencon.elearning.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.elearning.dto.teacher.TeacherForAvailableCourseDTO;
import com.lawencon.elearning.model.CourseStatus;
import java.time.LocalDate;
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

  private Boolean isRegist;

  private CourseStatus courseStatus;

  private String courseDescription;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate periodStart;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate periodEnd;

  private String categoryName;

  private String categoryCode;

  private TeacherForAvailableCourseDTO teacher;

  private Integer totalModule;

  private Integer moduleComplete;

  private Double percentProgress;

}
