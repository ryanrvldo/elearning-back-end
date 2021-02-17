package com.lawencon.elearning.model;

import com.lawencon.model.BaseMaster;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_m_courses", uniqueConstraints = {
    @UniqueConstraint(name = "bk_course", columnNames = {"code"})})
public class Course extends BaseMaster {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false, length = 100)
  private String code;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_course_type", foreignKey = @ForeignKey(name = "fk_type"),
      nullable = false)
  private CourseType courseType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_teacher", foreignKey = @ForeignKey(name = "fk_teacher"), nullable = false)
  private Teacher teacher;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_category", foreignKey = @ForeignKey(name = "fk_category"),
      nullable = false)
  private CourseCategory category;

  @Column(nullable = false)
  private Integer capacity;

  @Column(name = "period_start", nullable = false)
  private LocalDate periodStart;

  @Column(name = "period_end", nullable = false)
  private LocalDate periodEnd;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CourseStatus status;

}
