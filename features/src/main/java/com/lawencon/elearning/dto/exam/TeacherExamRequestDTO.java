package com.lawencon.elearning.dto.exam;

import java.time.LocalDateTime;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.lawencon.elearning.model.ExamType;
import lombok.Data;

/**
 * @author Dzaky Fadhilla Guci
 */
@Data
public class TeacherExamRequestDTO {

  @NotBlank
  @Size(min = 32, max = 36)
  private String moduleId;

  @NotBlank
  private String title;

  @NotBlank
  private String description;

  @NotNull
  @FutureOrPresent
  private LocalDateTime startTime;

  @NotNull
  @FutureOrPresent
  private LocalDateTime endTime;

  @NotBlank
  @Size(min = 32, max = 36)
  private String createdBy;

  @NotNull
  private ExamType type;


}
