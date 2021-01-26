package com.lawencon.elearning.service;

import com.lawencon.elearning.dto.ExperienceRequestDTO;
import com.lawencon.elearning.model.Experience;
import java.util.List;

/**
 * @author Rian Rivaldo
 */
public interface ExperienceService {

  void createExperience(ExperienceRequestDTO experienceRequest) throws Exception;

  Experience getById(String id) throws Exception;

  Experience getByCode(String code) throws Exception;

  List<Experience> getAllByTeacherId(String teacherId) throws Exception;

  void updateExperience(ExperienceRequestDTO experienceRequest) throws Exception;

}
