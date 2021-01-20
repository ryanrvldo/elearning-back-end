package com.lawencon.elearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

}
