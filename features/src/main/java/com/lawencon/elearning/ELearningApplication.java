package com.lawencon.elearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author Rian Rivaldo
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = "com.lawencon")
public class ELearningApplication {

  public static void main(String[] args) {
    SpringApplication.run(ELearningApplication.class, args);
  }

}
