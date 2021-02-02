package com.lawencon.elearning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.lawencon.elearning.dto.subject.CreateSubjectCategoryRequestDTO;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.ModuleService;
import com.lawencon.elearning.service.SubjectCategoryService;
import com.lawencon.elearning.service.UserService;

/**
 * @author Rian Rivaldo
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ELearningApplicationTest {

  @Autowired
  private UserService userService;

  @Autowired
  private SubjectCategoryService subjectCategoryService;

  @Autowired
  private ModuleService moduleService;

  @Test
  public void injectedComponentAreNotNull() {
    assertThat(userService).isNotNull();
    assertThat(subjectCategoryService).isNotNull();
    assertThat(moduleService).isNotNull();
  }

  @Test
  @Disabled
  public void initSubjectCategories() throws Exception {
    User user = userService.getByUsername("admin");
    assertThat(user).isNotNull();

    subjectCategoryService.addSubject(new CreateSubjectCategoryRequestDTO("SC-001", "JAVA",
        "Lorem ipsum bla bla bla for JAVA.", user.getId()), null);
    assertThatNoException();

    subjectCategoryService.addSubject(new CreateSubjectCategoryRequestDTO("SC-002", "ANGULAR",
        "Lorem ipsum bla bla bla for ANGULAR.", user.getId()), null);
    assertThatNoException();

    subjectCategoryService.addSubject(new CreateSubjectCategoryRequestDTO("SC-003", "SPRING",
        "Lorem ipsum bla bla bla for SPRING.", user.getId()), null);
    assertThatNoException();
  }

  @Test
  public void initModules() throws Exception {
    User user = userService.getByUsername("admin");
    assertThat(user).isNotNull();
  }
}
