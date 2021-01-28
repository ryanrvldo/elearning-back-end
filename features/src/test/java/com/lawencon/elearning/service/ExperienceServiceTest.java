package com.lawencon.elearning.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lawencon.elearning.dto.role.RoleResponseDto;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.User;
import javax.persistence.RollbackException;
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
class ExperienceServiceTest {

  @Autowired
  private RoleService roleService;

  @Autowired
  private UserService userService;

  @Test
  void injectedComponentAreNotNull() {
    assertThat(roleService).isNotNull();
    assertThat(userService).isNotNull();
  }

  @Test
  void testCreateUserAlreadyExists() throws Exception {
    RoleResponseDto roleResponse = roleService.findByCode("RL-001");
    User user = new User();
    user.setFirstName("Super Administrator");
    user.setUsername("superAdmin");
    user.setPassword("superAdmin");
    user.setEmail("superadmin@lawerning.com");
    Role role = new Role();
    role.setId(roleResponse.getId());
    role.setVersion(roleResponse.getVersion());
    user.setRole(role);
    assertThatThrownBy(() -> userService.addUser(user))
        .hasCauseInstanceOf(RollbackException.class);
  }

}