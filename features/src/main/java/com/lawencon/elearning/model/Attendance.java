package com.lawencon.elearning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.lawencon.model.BaseTransaction;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 *
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_r_attendances", uniqueConstraints = {
    @UniqueConstraint(name = "fk_module_student", columnNames = {"id_module", "id_student"})})
public class Attendance extends BaseTransaction {
  private static final long serialVersionUID = 1L;

  @Column(name = "is_verified", nullable = false)
  private Boolean isVerified;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_module", nullable = false, foreignKey = @ForeignKey(name = "fk_module"))
  private Module module;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_student", nullable = false, foreignKey = @ForeignKey(name = "fk_student"))
  private Student student;
}
