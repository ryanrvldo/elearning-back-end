package com.lawencon.elearning.model;

import com.lawencon.model.BaseMaster;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 *
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_m_subject_categories",
    uniqueConstraints = {@UniqueConstraint(name = "bk_subject", columnNames = {"code"})})
public class SubjectCategory extends BaseMaster {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false, length = 50)
  private String code;

  @Column(name = "subject_name", nullable = false, length = 100)
  private String subjectName;

  @Column(columnDefinition = "TEXT")
  private String description;
}
