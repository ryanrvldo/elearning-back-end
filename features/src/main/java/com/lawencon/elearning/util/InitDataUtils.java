package com.lawencon.elearning.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.RoleDao;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.User;

/**
 * Uncomment component annotation for init data to database
 *
 * @author Rian Rivaldo
 */
// @Component
public class InitDataUtils extends BaseServiceImpl implements CommandLineRunner {

  @Autowired
  private RoleDao roleDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private SecurityUtils securityUtils;

  @Override
  public void run(String... args) throws Exception {
    initData();
  }

  private void initData() throws Exception {
    initSuperAdmin();
    initRoles();
    initAdmin();
  }

  private void initSuperAdmin() throws Exception {
    Role role = new Role();
    role.setCode("RL-001");
    role.setName("Super Admin");
    roleDao.create(role);

    User user = new User();
    user.setFirstName("Super Administrator");
    user.setUsername("superAdmin");
    user.setPassword(securityUtils.getHashPassword("superAdmin"));
    user.setEmail("superadmin@lawerning.com");
    user.setRole(role);
    begin();
    userDao.createUser(user);
    commit();
  }

  private void initRoles() throws Exception {
    Role superAdminRole = roleDao.findByCode("RL-001");
    for (int i = 2; i <= 4; i++) {
      Role role = new Role();
      role.setCode("RL-00" + i);
      role.setName("Role-00" + i);
      role.setCreatedBy(superAdminRole.getId());
      roleDao.create(role);
    }
  }

  private void initAdmin() throws Exception {
    User user = new User();
    user.setFirstName("Administrator");
    user.setUsername("admin");
    user.setEmail("admin@lawerning.com");
    user.setPassword(securityUtils.getHashPassword("admin"));

    User superAdminUser = userDao.findByUsername("superAdmin");
    user.setCreatedBy(superAdminUser.getId());

    Role role = roleDao.findByCode("RL-002");
    user.setRole(role);
    begin();
    userDao.createUser(user);
    commit();
  }

}
