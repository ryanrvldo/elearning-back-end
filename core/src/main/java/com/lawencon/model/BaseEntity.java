package com.lawencon.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
  public static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @JsonIgnore
  @Column(name = "created_by")
  private String createdBy;

  @JsonIgnore
  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

  @JsonIgnore
  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "version")
  private Long version;

  public void setVersion(Long version) {
    if (version == null) {
      this.version = 0L;
    } else {
      this.version = version;
    }
  }
}
