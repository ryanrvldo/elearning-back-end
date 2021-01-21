package com.lawencon.elearning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.lawencon.model.BaseMaster;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 *
 **/
@Data
@Entity
@Table(name = "tb_m_subject_categories")
public class SubjectCategory extends BaseMaster {
  private static final long serialVersionUID = 1L;

  @Column(unique = true, nullable = false, length = 50)
  private String code;

  @Column(name = "subject_name", nullable = false, length = 100)
  private String subjectName;

  private String description;
}
