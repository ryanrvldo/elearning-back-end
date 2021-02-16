package com.lawencon.elearning.dto.module;

import com.lawencon.elearning.dto.ScheduleRequestDTO;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ModuleRequestDTO {

  @NotBlank
  @Size(max = 50)
  private String code;

  @NotBlank
<<<<<<< Updated upstream:features/src/main/java/com/lawencon/elearning/dto/module/ModuleRequestDTO.java
  private String title;
=======
  @Size(min = 32, max = 36)
  private String moduleCreatedBy;

  @NotBlank
  private String moduleTittle;
>>>>>>> Stashed changes:features/src/main/java/com/lawencon/elearning/dto/module/ModulRequestDTO.java

  @NotBlank
  private String description;

  @NotBlank
  @Size(min = 32, max = 36)
  private String courseId;

  @NotBlank
  @Size(min = 32, max = 36)
  private String subjectId;

  @NotNull
  private ScheduleRequestDTO schedule;

  @NotBlank
  @Size(min = 32, max = 36)
  private String createdBy;

}
