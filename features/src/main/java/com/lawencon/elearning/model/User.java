package com.lawencon.elearning.model;

import com.lawencon.model.BaseMaster;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_m_users")
public class User extends BaseMaster {

  private static final long serialVersionUID = 1L;

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "last_name", length = 50)
  private String lastName;

  @Column(unique = true, nullable = false, length = 100)
  private String username;

  @Column(name = "user_password", nullable = false)
  private String password;

  @Column(unique = true, nullable = false, length = 100)
  private String email;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_role", nullable = false)
  private Role role;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "id_photo")
  private File userPhoto;

}
