package com.lawencon.elearning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lawencon.model.BaseMaster;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "tb_m_modules")
public class Module extends BaseMaster {
  private static final long serialVersionUID = 1L;

  @Column(unique = true, nullable = false, length = 50)
  private String code;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_schedule")
  private Schedule schedule;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_course", nullable = false)
  private Course course;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_subject", nullable = false)
  private SubjectCategory subject;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "module_files",
      joinColumns = {
          @JoinColumn(name = "id_module")
      },
      inverseJoinColumns = {
          @JoinColumn(name = "id_file")
      }
  )
  private Set<File> files;

}
