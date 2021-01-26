package com.lawencon.elearning;

import com.lawencon.elearning.dto.ExperienceRequestDTO;
import com.lawencon.elearning.service.ExperienceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author Rian Rivaldo
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.lawencon")
public class ELearningApplication {

  public static void main(String[] args) {
    SpringApplication.run(ELearningApplication.class, args);
  }


  @Bean
  public CommandLineRunner test(ExperienceService experienceService) {
    return args -> {
      ExperienceRequestDTO requestDTO = new ExperienceRequestDTO();
      requestDTO.setTitle("Bachelor Degree");
    };
  }
}
