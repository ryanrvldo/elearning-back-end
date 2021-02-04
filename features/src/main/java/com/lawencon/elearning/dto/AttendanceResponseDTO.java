package com.lawencon.elearning.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.elearning.dto.student.StudentByCourseResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author WILLIAM
 *
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AttendanceResponseDTO extends StudentByCourseResponseDTO {

  private String attendanceId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime attendanceTime;
  private Long attendanceVersion;
  private boolean attendanceIsVerified;

}
