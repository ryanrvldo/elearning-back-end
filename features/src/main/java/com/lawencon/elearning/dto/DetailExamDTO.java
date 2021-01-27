package com.lawencon.elearning.dto;

import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class DetailExamDTO {

  private String fileId;
  private Long fileVersion;
  private String examId;
  private Long examVersion;
  private String studentId;
  private Long studentVersion;
  private String createdBy;

}
