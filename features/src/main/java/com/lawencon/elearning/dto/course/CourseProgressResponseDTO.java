package com.lawencon.elearning.dto.course;

import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseProgressResponseDTO {

  private String courseId;
  private String courseName;
  private Integer totalModule;
  private Integer moduleComplete;
}
