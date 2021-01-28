package com.lawencon.elearning.dto;

import com.lawencon.elearning.model.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Rian Rivaldo
 */
@Data
@AllArgsConstructor
public class FileResponseDto {

  public String id;
  public String fileName;
  public FileType fileType;
  public Long size;
  public String contentType;
  public Long version;

}
