package com.lawencon.elearning.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.lawencon.model.BaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_m_users",
    uniqueConstraints = {@UniqueConstraint(name = "bk_username", columnNames = {"username"}),
        @UniqueConstraint(name = "bk_email", columnNames = {"email"})})
public class User extends BaseMaster {

  private static final long serialVersionUID = 1L;

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "last_name", length = 50)
  private String lastName;

  @Column(nullable = false, length = 100)
  private String username;

  @Column(name = "user_password", nullable = false)
  private String password;

  @Column(nullable = false, length = 100)
  private String email;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_role", nullable = false, foreignKey = @ForeignKey(name = "fk_role"))
  private Role role;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "id_photo", foreignKey = @ForeignKey(name = "fk_photo"))
  private File userPhoto;

}
