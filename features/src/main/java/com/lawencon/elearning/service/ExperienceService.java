package com.lawencon.elearning.service;

import com.lawencon.elearning.dto.experience.ExperienceCreateRequestDTO;
import com.lawencon.elearning.dto.experience.ExperienceResponseDto;
import com.lawencon.elearning.dto.experience.ExperienceUpdateRequestDto;
import com.lawencon.elearning.model.Experience;
import java.util.List;

/**
 * @author Rian Rivaldo
 */
public interface ExperienceService {

  ExperienceResponseDto createExperience(ExperienceCreateRequestDTO experienceRequest)
      throws Exception;

  Experience getById(String id) throws Exception;

  Experience getByCode(String code) throws Exception;

  List<ExperienceResponseDto> getAllByTeacherId(String teacherId) throws Exception;

  ExperienceResponseDto updateExperience(ExperienceUpdateRequestDto experienceRequest)
      throws Exception;

  void deleteExperience(String id) throws Exception;

}
