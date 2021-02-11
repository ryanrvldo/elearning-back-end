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
public class DashboardStudentResponseDto {

  private Integer verified;

  private Integer active;

  private Integer inactive;

  private Integer male;

  private Integer female;

  private Integer total;

  private Integer registeredToCourse;

}
