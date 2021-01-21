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
@Table(name = "tb_r_forums")
public class Forum extends BaseTransaction {

  private static final long serialVersionUID = 1L;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_module", nullable = false)
  private Module module;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user", nullable = false)
  private User user;
}
