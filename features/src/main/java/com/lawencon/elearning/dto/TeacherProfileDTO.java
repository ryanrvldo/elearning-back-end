package com.lawencon.elearning.dto;

import java.util.List;
import com.lawencon.elearning.model.Experience;
import lombok.Data;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
public class TeacherProfileDTO {

  private TeacherResponseDTO teacher;
  private List<Experience> experiences;



}
