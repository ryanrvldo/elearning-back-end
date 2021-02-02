package com.lawencon.elearning.dto.file;

import com.lawencon.elearning.model.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rian Rivaldo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDto {

  public String id;
  public String fileName;
  public FileType fileType;
  public Long size;
  public String contentType;
  public Long version;

}
