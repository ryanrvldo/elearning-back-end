package com.lawencon.elearning.dto.exam;

import java.time.LocalDateTime;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.elearning.model.ExamType;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */
@Data
public class TeacherExamRequestDTO {

  @NotBlank
  private String moduleId;

  @Min(0)
  private Long moduleVersion;

  @NotBlank
  private String title;

  @NotBlank
  private String description;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @FutureOrPresent
  private LocalDateTime startTime;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @FutureOrPresent
  private LocalDateTime endTime;

  @NotBlank
  private String createdBy;

  @NotNull
  private ExamType type;


}
