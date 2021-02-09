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
import lombok.ToString;

/**
 * @author : Galih Dika Permana
 *
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"data", "user", "modules"})
@Entity
@Table(name = "tb_r_files")
public class File extends BaseTransaction {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false)
  private String name;

  @Lob
  @Column(nullable = false)
  private byte[] data;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FileType type;

  @Column(nullable = false, length = 50)
  private String contentType;

  @Column(nullable = false)
  private Long size;

  @JsonIgnore
  @OneToOne(mappedBy = "userPhoto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "id_photo")
  private User user;

  @JsonIgnore
  @ManyToMany(mappedBy = "files", fetch = FetchType.LAZY)
  private Set<Module> modules;



}
