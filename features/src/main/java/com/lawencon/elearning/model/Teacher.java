package com.lawencon.elearning.model;

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
@Table(name = "tb_m_teachers",
    uniqueConstraints = {
        @UniqueConstraint(name = "bk_teacher", columnNames = {"code"})})
public class Teacher extends BaseMaster {
  private static final long serialVersionUID = 1L;

  @Column(nullable = false, length = 50)
  private String code;

  @Column(name = "title_degree", nullable = false, length = 50)
  private String titleDegree;

  @Column(nullable = false, length = 20)
  private String phone;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_user"))
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;
}