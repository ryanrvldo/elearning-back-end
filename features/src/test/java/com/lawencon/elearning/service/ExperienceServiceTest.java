package com.lawencon.elearning.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Rian Rivaldo
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ExperienceServiceTest {

  @Autowired
  private ExperienceService experienceService;

  @Autowired
  private UserService userService;

  @Autowired
  private TeacherService teacherService;

  @Test
  void injectedComponentAreNotNull() {
    assertThat(experienceService).isNotNull();
    assertThat(userService).isNotNull();
    assertThat(teacherService).isNotNull();
  }

  @Test
  void testCreateExperience() throws Exception {
    User user = new User();
    user.setCreatedAt(LocalDateTime.now());
    user.setCreatedBy("SUPER_ADMIN");
    user.setEmail("galih@gmail.com");
    user.setFirstName("Galih");
    user.setLastName("Joget");
    user.setUsername("galihjoget");
    user.setPassword("rahasia");

    Role role = new Role();
    role.setId("1");
    user.setRole(role);
    userService.addUser(user);
    assertThat(user.getId()).isNotNull();

    Teacher teacher = new Teacher();
    teacher.setUser(user);
    teacher.setCreatedAt(LocalDateTime.now());
    teacher.setCreatedBy("SUPER_ADMIN");
    teacher.setCode("TCHR-0001");
    teacher.setGender(Gender.FEMALE);
    teacher.setPhone("0281921839");
    teacher.setTitleDegree("S.KOM");
//    teacherService.saveTeacher(teacher);
    assertThat(teacher.getId()).isNotNull();
  }

}