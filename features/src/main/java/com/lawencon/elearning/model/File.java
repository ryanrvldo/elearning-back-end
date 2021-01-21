package com.lawencon.elearning.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.lawencon.model.BaseTransaction;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 *
 **/
@Data
@Entity
@Table(name = "tb_r_files")
public class File extends BaseTransaction {
  private static final long serialVersionUID = 1L;

  @Lob
  @Column(nullable = false, length = 100000)
  private byte[] data;

  @Column(nullable = false)
  private Double size;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FileType type;

  @Column(nullable = false, length = 10)
  private String extension;

  @Column(nullable = false, length = 50)
  private String name;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "id_user")
  private User user;
}
