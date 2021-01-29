package com.lawencon.elearning.controller;

import com.lawencon.elearning.dto.experience.ExperienceCreateRequestDTO;
import com.lawencon.elearning.dto.experience.ExperienceUpdateRequestDto;
import com.lawencon.elearning.service.ExperienceService;
import com.lawencon.elearning.util.WebResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rian Rivaldo
 */
@RestController
@RequestMapping(value = "/experience")
public class ExperienceController {

  @Autowired
  private ExperienceService experienceService;

  @PostMapping
  public ResponseEntity<?> createExperience(@RequestBody ExperienceCreateRequestDTO requestBody)
      throws Exception {
    return WebResponseUtils
        .createWebResponse(experienceService.createExperience(requestBody), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<?> updateExperience(@RequestBody ExperienceUpdateRequestDto requestBody)
      throws Exception {
    return WebResponseUtils
        .createWebResponse(experienceService.updateExperience(requestBody), HttpStatus.OK);
  }

  @DeleteMapping(path = "{id}")
  public ResponseEntity<?> deleteExperience(@PathVariable("id") String id) throws Exception {
    experienceService.deleteExperience(id);
    return WebResponseUtils
        .createWebResponse("Experience has been deleted successfully with id: " + id,
            HttpStatus.OK);
  }

}
