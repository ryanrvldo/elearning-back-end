package com.lawencon.elearning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lawencon.model.BaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 *
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_m_course_categories",
    uniqueConstraints = {@UniqueConstraint(name = "bk_category", columnNames = {"code"})})
@JsonInclude(Include.NON_NULL)
public class CourseCategory extends BaseMaster {
  private static final long serialVersionUID = 1L;

  @Column(nullable = false, length = 50)
  private String code;

  @Column(name = "category_name", nullable = false, length = 100)
  private String name;
}
