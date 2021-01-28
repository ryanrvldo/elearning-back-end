package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ExperienceDao;
import com.lawencon.elearning.dto.ExperienceRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.Experience;
import com.lawencon.elearning.service.ExperienceService;

/**
 * @author Rian Rivaldo
 */
@Service
public class ExperienceServiceImpl extends BaseServiceImpl implements ExperienceService {

  @Autowired
  private ExperienceDao experienceDao;

  @Override
  public void createExperience(ExperienceRequestDTO experienceRequest) throws Exception {
    Experience experience = new Experience();
    experience.setTitle(experienceRequest.getTitle());
    experience.setDescription(experience.getDescription());
    experience.setStartDate(experienceRequest.getStartDate());
    experience.setEndDate(experienceRequest.getEndDate());
    experience.setCreatedAt(LocalDateTime.now());
    experience.setCreatedBy(experienceRequest.getUserId());

    experienceDao.create(experience);
  }

  @Override
  public Experience getById(String id) throws Exception {
    return Optional.ofNullable(experienceDao.findById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public Experience getByCode(String code) throws Exception {
    return Optional.ofNullable(experienceDao.findByCode(code))
        .orElseThrow(() -> new DataIsNotExistsException("code", code));
  }

  @Override
  public List<Experience> getAllByTeacherId(String teacherId) throws Exception {
    List<Experience> experiences = experienceDao.findAllByTeacherId(teacherId);
    if (experiences.isEmpty()) {
      return null;
    }
    return experiences;
  }

  @Override
  public void updateExperience(ExperienceRequestDTO experienceRequest) throws Exception {
    Experience prevExperience = experienceDao.findById(experienceRequest.getId());
    Experience newExperience = new Experience();
    newExperience.setCreatedAt(prevExperience.getCreatedAt());
    newExperience.setCreatedBy(prevExperience.getCreatedBy());
    newExperience.setUpdatedAt(LocalDateTime.now());
    newExperience.setUpdatedBy(experienceRequest.getUserId());

    newExperience.setId(experienceRequest.getId());
    newExperience.setTitle(experienceRequest.getTitle());
    newExperience.setDescription(experienceRequest.getDescription());
    newExperience.setStartDate(experienceRequest.getStartDate());
    newExperience.setEndDate(experienceRequest.getEndDate());

    experienceDao.update(newExperience);
  }

}
