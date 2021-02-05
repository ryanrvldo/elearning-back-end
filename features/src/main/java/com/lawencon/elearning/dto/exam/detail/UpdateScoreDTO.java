package com.lawencon.elearning.dto.exam.detail;

import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class UpdateScoreDTO {
  private String id;
  private String updatedBy;
  private Double grade;
}
