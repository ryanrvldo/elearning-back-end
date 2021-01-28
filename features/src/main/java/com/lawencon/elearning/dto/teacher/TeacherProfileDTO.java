package com.lawencon.elearning.dto.teacher;

import java.util.List;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lawencon.elearning.model.Experience;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
@JsonInclude(Include.NON_NULL)
public class TeacherProfileDTO {

  @NotNull
  private TeacherResponseDTO teacher;
  private List<Experience> experiences;

}
