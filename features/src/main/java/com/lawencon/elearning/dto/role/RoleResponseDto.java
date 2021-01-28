package com.lawencon.elearning.dto.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Rian Rivaldo
 */
@Data
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class RoleResponseDto {

  private String id;

  private String code;

  private String name;

  private Long version;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

}
