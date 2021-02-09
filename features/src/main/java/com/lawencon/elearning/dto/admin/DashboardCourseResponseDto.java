package com.lawencon.elearning.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCourseResponseDto {

  private Integer registeredStudent;

  private Integer registeredTeacher;

  private Integer available;

  private Integer expired;

  private Integer active;

  private Integer inactive;

  private Integer total;

}
