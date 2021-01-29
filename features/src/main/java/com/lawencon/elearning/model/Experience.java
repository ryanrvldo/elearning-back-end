package com.lawencon.elearning.model;

import com.lawencon.model.BaseMaster;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 *
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_m_experiences")
public class Experience extends BaseMaster {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false, length = 35)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String description;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_teacher", nullable = false, foreignKey = @ForeignKey(name = "fk_teacher"))
  private Teacher teacher;

}
