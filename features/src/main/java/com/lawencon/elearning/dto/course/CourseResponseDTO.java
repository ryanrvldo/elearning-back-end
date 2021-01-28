package com.lawencon.elearning.dto.course;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.lawencon.elearning.model.CourseStatus;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 */
@Data
public class CourseResponseDTO {

  @NotBlank
  private String courseId;
  @NotBlank
  private String courseCode;
  @NotBlank
  private String typeName;
  @NotNull
  private Integer courseCapacity;

  private CourseStatus courseStatus;

  private String courseDescription;
  @NotNull
  private LocalDateTime coursePeriodStart;
  @NotNull
  private LocalDateTime coursePeriodEnd;
  @NotBlank
  private String teacherId;
  @NotBlank
  private String teacherCode;
  @NotBlank
  private String userFirstName;
  @NotBlank
  private String userLastName;
  @NotBlank
  private String teacherTittle;
  @NotBlank
  private String categoryCode;
  @NotBlank
  private String categoryName;

}
