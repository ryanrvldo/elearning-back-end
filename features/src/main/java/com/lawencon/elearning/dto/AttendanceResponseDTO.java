package com.lawencon.elearning.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @author WILLIAM
 *
 */

@Data
public class AttendanceResponseDTO {

  private String attendanceId;
  private String firstName;
  private String lastName;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime attendanceTime;
  private Long attendanceVersion;
  private boolean attendanceIsVerified;

}
