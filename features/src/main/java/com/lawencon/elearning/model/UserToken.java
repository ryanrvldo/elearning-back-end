package com.lawencon.elearning.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.lawencon.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
    name = "tb_m_user_tokens",
    uniqueConstraints = {
        @UniqueConstraint(name = "bk_token", columnNames = "token")
    }
)
public class UserToken extends BaseEntity {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false)
  private String token;

  @Column(nullable = false)
  private LocalDateTime expiredAt;

  private LocalDateTime confirmedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false, name = "id_user", foreignKey = @ForeignKey(name = "fk_user"))
  private User user;

  public UserToken(String token, LocalDateTime expiredAt, User user) {
    this.token = token;
    this.expiredAt = expiredAt;
    this.user = user;
  }
}
