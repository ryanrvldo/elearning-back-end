package com.lawencon.elearning.dto.module;

import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class ModuleListReponseDTO {

  private String id;
  private String code;
  private String title;
  private String description;
  private String subjectName;
  private Boolean isActive;

}
