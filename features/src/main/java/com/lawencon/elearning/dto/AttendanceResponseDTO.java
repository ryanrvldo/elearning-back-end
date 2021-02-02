package com.lawencon.elearning.dto;

import java.time.LocalDateTime;
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
  private LocalDateTime attendanceTime;
  private Long attendanceVersion;
  private boolean attendanceIsVerified;

}
