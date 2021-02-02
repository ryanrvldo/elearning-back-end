package com.lawencon.elearning.dto.course;

import java.util.List;
import com.lawencon.elearning.dto.module.ModuleResponseDTO;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class DetailCourseResponseDTO {

  private String id;
  private String code;
  private String name;
  private List<ModuleResponseDTO> listModule;
}
