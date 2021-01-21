package com.lawencon.elearning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.lawencon.model.BaseTransaction;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 *
 **/
@Data
@Entity
@Table(name = "tb_r_attendances")
public class Attendance extends BaseTransaction {
  private static final long serialVersionUID = 1L;

  @Column(name = "is_verified", nullable = false)
  private Boolean isVerified;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_module", nullable = false)
  private Module module;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_student", nullable = false)
  private Student student;
}
