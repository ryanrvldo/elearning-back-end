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

  private String id;
  private String fileName;
  private FileType fileType;
  private Long size;
  private String contentType;
  private Long version;

}
