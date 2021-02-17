package com.lawencon.elearning;

import static org.assertj.core.api.Assertions.assertThat;

import com.lawencon.elearning.service.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Rian Rivaldo
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ELearningApplicationTest {

  @Autowired
  private FileService fileService;

  @Test
  public void injectedComponentAreNotNull() {
    assertThat(fileService).isNotNull();
  }

}
