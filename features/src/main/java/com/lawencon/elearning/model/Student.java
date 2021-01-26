package com.lawencon.elearning.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "tb_m_students",
    uniqueConstraints = {
        @UniqueConstraint(name = "bk_student", columnNames = {"code"})})
public class Student extends BaseMaster {
  private static final long serialVersionUID = 1L;

  @Column(nullable = false, length = 50)
  private String code;

  @Column(nullable = false, length = 20)
  private String phone;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_user"))
  private User user;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinTable(name = "student_course",
      joinColumns = {
          @JoinColumn(name = "id_student")},
      inverseJoinColumns = {
          @JoinColumn(name = "id_course", foreignKey = @ForeignKey(name = "fk_course"))})
  private Set<Course> courses = new HashSet<>();
}
