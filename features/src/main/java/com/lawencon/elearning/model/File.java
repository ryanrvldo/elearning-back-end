package com.lawencon.elearning.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tb_r_files")
public class File extends BaseTransaction {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, length = 20)
  private String extension;

  @Lob
  @Column(nullable = false)
  private byte[] data;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FileType type;

  @Column(nullable = false, length = 50)
  private String contentType;

  @Column(nullable = false)
  private long size;

  @OneToOne(mappedBy = "userPhoto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "id_photo")
  private User user;

  @JsonIgnore
  @ManyToMany(mappedBy = "files", fetch = FetchType.LAZY)
  private Set<Module> modules;

}
