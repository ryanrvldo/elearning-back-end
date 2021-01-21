package com.lawencon.elearning.dao.impl;

import org.springframework.stereotype.Repository;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.User;

/**
 * @author Rian Rivaldo
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

  @Override
  public void createUser(User user) throws Exception {
    save(user, null, null, true, true);
  }

  @Override
  public User findById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public User findByUsername(String username) throws Exception {
    String query = new StringBuilder().append(
        "SELECT u.id, u.email, u.username, u.password, u.isActive, r.id AS roleId, u.role.code, u.role.name ")
        .append("FROM User AS u INNER JOIN Role AS r ON r.id = u.role.id ")
        .append("WHERE u.username = ?1 ").toString();
    Object[] objArr =
        createQuery(query, Object[].class).setParameter(1, username).getSingleResult();
    User user = new User();
    user.setId((String) objArr[0]);
    user.setEmail((String) objArr[1]);
    user.setUsername((String) objArr[2]);
    user.setPassword((String) objArr[3]);
    user.setIsActive((Boolean) objArr[4]);

    Role role = new Role();
    role.setId((String) objArr[5]);
    role.setCode((String) objArr[6]);
    role.setName((String) objArr[7]);
    user.setRole(role);
    return user;
  }

}
