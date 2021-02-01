package com.lawencon.elearning.service.impl;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ExperienceDao;
import com.lawencon.elearning.dto.experience.ExperienceCreateRequestDTO;
import com.lawencon.elearning.dto.experience.ExperienceResponseDto;
import com.lawencon.elearning.dto.experience.ExperienceUpdateRequestDto;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.Experience;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.service.ExperienceService;
import com.lawencon.elearning.util.ValidationUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rian Rivaldo
 */
@Service
public class ExperienceServiceImpl extends BaseServiceImpl implements ExperienceService {

  @Autowired
  private ExperienceDao experienceDao;

  @Autowired
  private ValidationUtil validationUtil;

  @Override
  public ExperienceResponseDto createExperience(ExperienceCreateRequestDTO experienceRequest)
      throws Exception {
    validationUtil.validate(experienceRequest);

    Experience experience = new Experience();
    experience.setTitle(experienceRequest.getTitle());
    experience.setDescription(experienceRequest.getDescription());
    experience.setStartDate(experienceRequest.getStartDate());
    experience.setEndDate(experienceRequest.getEndDate());
    experience.setCreatedAt(LocalDateTime.now());
    experience.setCreatedBy(experienceRequest.getUserId());

    Teacher teacher = new Teacher();
    teacher.setId(experienceRequest.getTeacherId());
    experience.setTeacher(teacher);
    experienceDao.create(experience);

    return mapEntityToResponse(experience);
  }

  @Override
  public Experience getById(String id) throws Exception {
    validationUtil.validateUUID(id);

    return Optional.ofNullable(experienceDao.findById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public Experience getByCode(String code) throws Exception {
    validationUtil.validateCode(code, 50);

    return Optional.ofNullable(experienceDao.findByCode(code))
        .orElseThrow(() -> new DataIsNotExistsException("code", code));
  }

  @Override
  public List<ExperienceResponseDto> getAllByTeacherId(String teacherId) throws Exception {
    validationUtil.validateUUID(teacherId);

    List<Experience> experiences = experienceDao.findAllByTeacherId(teacherId);
    if (experiences.isEmpty()) {
      return null;
    }
    return experiences.stream()
        .map(this::mapEntityToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public ExperienceResponseDto updateExperience(ExperienceUpdateRequestDto experienceRequest)
      throws Exception {
    validationUtil.validate(experienceRequest);

    Experience prevExperience = experienceDao.findById(experienceRequest.getId());
    Experience newExperience = new Experience();
    setupUpdatedValue(newExperience, () -> prevExperience);
    newExperience.setUpdatedBy(experienceRequest.getUserId());
    newExperience.setTitle(experienceRequest.getTitle());
    newExperience.setDescription(experienceRequest.getDescription());
    newExperience.setStartDate(experienceRequest.getStartDate());
    newExperience.setEndDate(experienceRequest.getEndDate());
    newExperience.setTeacher(prevExperience.getTeacher());

    experienceDao.update(newExperience);
    return mapEntityToResponse(newExperience);
  }

  @Override
  public void deleteExperience(String id) throws Exception {
    validationUtil.validateUUID(id);
    try {
      begin();
      experienceDao.delete(id);
      commit();
    } catch (Exception e) {
      if (e.getMessage().equals("ID Not Found")) {
        throw new DataIsNotExistsException("id", id);
      }
    }
  }

  private ExperienceResponseDto mapEntityToResponse(Experience experience) {
    return new ExperienceResponseDto(
        experience.getId(),
        experience.getTitle(),
        experience.getDescription(),
        experience.getStartDate(),
        experience.getEndDate()
    );
  }

}
